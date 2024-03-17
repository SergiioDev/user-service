package com.ecommerce.userservice.service.mapper;

import com.ecommerce.userservice.models.User;
import com.ecommerce.userservice.models.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public static User mapFromDto(UserDto userDto){
        return User.builder()
                .name(userDto.getName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .birthDate(userDto.getBirthDate())
                .password(userDto.getPassword())
                .build();
    }
}
