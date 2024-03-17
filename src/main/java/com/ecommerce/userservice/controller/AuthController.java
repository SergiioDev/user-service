package com.ecommerce.userservice.controller;

import com.ecommerce.userservice.exceptions.UserNotFoundException;
import com.ecommerce.userservice.models.TokenResponse;
import com.ecommerce.userservice.models.dto.UserDto;
import com.ecommerce.userservice.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/ecommerce/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
        try{
            TokenResponse tokenResponse = jwtService.login(userDto);
            LOGGER.info("Token created for user {}  token: {} ", userDto.getEmail(), tokenResponse.getToken());
            return new ResponseEntity<>(tokenResponse, HttpStatus.CREATED);
        }catch (Exception e) {
            LOGGER.info("Something happened when creating token for user {} cause: {}", userDto.getEmail(), e.getCause());
            return ResponseEntity.internalServerError().build();
        }
    }

}
