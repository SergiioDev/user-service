package com.ecommerce.userservice.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ecommerce.userservice.controller.AuthController;
import com.ecommerce.userservice.exceptions.InvalidPayloadException;
import com.ecommerce.userservice.exceptions.UserNotFoundException;
import com.ecommerce.userservice.models.TokenResponse;
import com.ecommerce.userservice.models.User;
import com.ecommerce.userservice.models.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtService.class);
    private final UserService userService;

    private final JWTVerifier jwtVerifier;

    private final Algorithm algorithm;

    public JwtService(UserService userService, Algorithm algorithm, JWTVerifier jwtVerifier){
        this.userService = userService;
        this.algorithm = algorithm;
        this.jwtVerifier = jwtVerifier;
    }

    public String createToken(User user) {
        long expirationTimeMillis = 60000L; // 1 minute
        Date issuedAt = new Date();
        Date expirationTime = new Date(issuedAt.getTime() + expirationTimeMillis);

        return JWT.create()
                .withClaim("user_id", user.getId())
                .withIssuer("e-commerce")
                .withIssuedAt(issuedAt)
                .withExpiresAt(expirationTime)
                .withJWTId(generateSecureRandomJwtId())
                .sign(algorithm);
    }


    private String generateSecureRandomJwtId() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    public Long getUserIdClaim(DecodedJWT decodedJWT) throws InvalidPayloadException {
        return Optional.ofNullable(decodedJWT)
                .map(d -> d.getClaim("user_id"))
                .map(Claim::asLong)
                .orElseThrow(() -> new InvalidPayloadException("Invalid payload"));
    }

    public TokenResponse login(UserDto userDto) throws UserNotFoundException {
       User user = userService.getByEmail(userDto.getEmail());
       String token = createToken(user);

       return TokenResponse.builder()
               .token(token)
               .build();
    }

    public boolean isTokenValid(String jwtToken) {
        try {
            DecodedJWT decodedJWT = jwtVerifier.verify(jwtToken);
            Long id = getUserIdClaim(decodedJWT);
            User user = userService.getById(id);
            LOGGER.info("Token validated for user {}" ,user.getEmail());
            return true;
        } catch (Exception e) {
            LOGGER.error("Could not validate the token, error: {}",e.getMessage());
        }
        return false;
    }

}