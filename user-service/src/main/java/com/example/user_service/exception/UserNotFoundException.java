package com.example.user_service.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
    
    public UserNotFoundException(Integer id) {
        super("User not found with ID: " + id);
    }
    
    public UserNotFoundException(String field, String value) {
        super("User not found with " + field + ": " + value);
    }
}