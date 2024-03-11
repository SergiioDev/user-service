package com.ecommerce.userservice.controller;

import com.ecommerce.userservice.exceptions.UserNotFoundException;
import com.ecommerce.userservice.models.TokenResponse;
import com.ecommerce.userservice.models.User;
import com.ecommerce.userservice.models.dto.UserDto;
import com.ecommerce.userservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/ecommerce/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getUsers(){
        try{
            List<User> users = userService.getUsers();
            LOGGER.info("Users retrieved");
            return ResponseEntity.ok().body(users);
        }catch (Exception e){
            LOGGER.error("Something happened while retrieving users {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }


    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto){
        try{
            User savedUser = userService.save(userDto);
            LOGGER.info("User created with id {}", savedUser.getId());
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        }catch (Exception e){
            LOGGER.error("Something happened while retrieving users {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> logIn(@RequestBody UserDto userDto){
        try {
           TokenResponse tokenResponse = userService.logIn(userDto);
           LOGGER.info("Token {} created for {} ",tokenResponse.getToken(), userDto.getEmail());
            return new ResponseEntity<>(tokenResponse, HttpStatus.CREATED);
        }catch (UserNotFoundException e) {
            LOGGER.error("Error creating token for user {} cause: {} ", userDto.getEmail(), e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (DataAccessException e) {
            LOGGER.error("Error creating token for user {} cause: {} ", userDto.getEmail(), e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
