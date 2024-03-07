package com.ecommerce.userservice.models;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Entity(name = "users")
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String lastName;

    @Column(unique = true, length = 100)
    private String email;

    @Temporal(value = TemporalType.DATE)
    private Date birthDate;
}