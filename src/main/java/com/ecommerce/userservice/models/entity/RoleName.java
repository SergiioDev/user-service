package com.ecommerce.userservice.models.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RoleName {
    USER,
    VENDOR,
    ADMIN
}