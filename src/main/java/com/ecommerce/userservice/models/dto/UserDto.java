package com.ecommerce.userservice.models.dto;

import com.ecommerce.userservice.models.User;
import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class UserDto {
    private String name;

    private String lastName;

    private String email;

    private Date birthDate;
}
