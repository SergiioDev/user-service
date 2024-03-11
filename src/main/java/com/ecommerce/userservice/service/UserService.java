package com.ecommerce.userservice.service;

import com.ecommerce.userservice.exceptions.UserNotFoundException;
import com.ecommerce.userservice.models.TokenResponse;
import com.ecommerce.userservice.models.User;
import com.ecommerce.userservice.models.dto.UserDto;
import com.ecommerce.userservice.repository.UserRepository;
import com.ecommerce.userservice.service.mapper.UserMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, JwtService jwtService){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }


    @Transactional
    public List<User> getUsers() throws DataAccessException {
        return userRepository.findAll();
    }

    @Transactional
    public User save(UserDto userDto) throws DataAccessException {
       userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
       return userRepository.save(UserMapper.mapFromDto(userDto));
    }


    @Transactional
    public TokenResponse logIn(UserDto userDto) throws UserNotFoundException, DataAccessException{
        return userRepository.findByEmail(userDto.getEmail())
                .filter(user -> passwordEncoder.matches(userDto.getPassword(), user.getPassword()))
                .map(user -> TokenResponse.builder()
                        .token(jwtService.createToken(user))
                        .build())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

}
