package com.ecommerce.userservice.service;

import com.ecommerce.userservice.models.User;
import com.ecommerce.userservice.models.dto.UserDto;
import com.ecommerce.userservice.repository.UserRepository;
import com.ecommerce.userservice.service.mapper.UserMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    @Transactional
    public List<User> getUsers() throws DataAccessException {
        return userRepository.findAll();
    }

    @Transactional
    public User save(UserDto userDto) throws DataAccessException {
       return userRepository.save(UserMapper.mapFromDto(userDto));
    }

}
