package com.ecommerce.userservice.models.dto;

import com.ecommerce.userservice.models.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    private String name;

    private String lastName;

    private String email;

    private Date birthDate;

    private String password;

    private Set<String> roles;
}
