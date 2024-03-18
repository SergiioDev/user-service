package com.ecommerce.userservice.service;

import com.ecommerce.userservice.exceptions.UserNotFoundException;
import com.ecommerce.userservice.models.entity.User;
import com.ecommerce.userservice.models.dto.UserDto;
import com.ecommerce.userservice.repository.UserRepository;
import com.ecommerce.userservice.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = new BCryptPasswordEncoder();
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
    public User getById(Long id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Transactional
    public User getByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

}
