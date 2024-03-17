package com.ecommerce.userservice.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class TokenResponse {
    private String token;
}