package com.example.user_service.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
    
    public static UserAlreadyExistsException withAccountId(String accountId) {
        return new UserAlreadyExistsException("User already exists with account ID: " + accountId);
    }
}