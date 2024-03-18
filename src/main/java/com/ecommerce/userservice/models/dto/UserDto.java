package com.ecommerce.userservice.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    private String name;

    private String lastName;

    private String email;

    private Date birthDate;

    private String password;
}
