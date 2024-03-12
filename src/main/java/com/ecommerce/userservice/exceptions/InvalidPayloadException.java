package com.ecommerce.userservice.exceptions;

public class InvalidPayloadException extends Exception {
    public InvalidPayloadException(String message) {
        super(message);
    }
}
