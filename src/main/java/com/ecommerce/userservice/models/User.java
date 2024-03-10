package com.ecommerce.userservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String lastName;

    @Column(unique = true, length = 100)
    private String username;

    @Column(unique = true, length = 100)
    private String email;

    @Temporal(value = TemporalType.DATE)
    private Date birthDate;

    private String password;
}