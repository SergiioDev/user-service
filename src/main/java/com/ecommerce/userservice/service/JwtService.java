package com.ecommerce.userservice.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.ecommerce.userservice.exceptions.InvalidPayloadException;
import com.ecommerce.userservice.exceptions.UserNotFoundException;
import com.ecommerce.userservice.models.TokenResponse;
import com.ecommerce.userservice.models.User;
import com.ecommerce.userservice.models.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtService {

    @Value("${spring.custom.secret}")
    private String secret;

    private final UserService userService;

    public JwtService(UserService userService){
        this.userService = userService;
    }

    public String createToken(User user) {
        long expirationTimeMillis = 3600000L;
        Date issuedAt = new Date();
        Date expirationTime = new Date(issuedAt.getTime() + expirationTimeMillis);

        return JWT.create()
                .withClaim("user_id", user.getId())
                .withIssuer("e-commerce")
                .withIssuedAt(issuedAt)
                .withExpiresAt(expirationTime)
                .withJWTId(generateSecureRandomJwtId())
                .sign(Algorithm.HMAC512(secret));
    }


    private String generateSecureRandomJwtId() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    public Long getUserIdClaim(String token) throws InvalidPayloadException {
        return Optional.of(JWT.decode(token))
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


    public Boolean validateToken(String token) {
        try{
            Long id = getUserIdClaim(token);
            return userService.getById(id) != null;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}