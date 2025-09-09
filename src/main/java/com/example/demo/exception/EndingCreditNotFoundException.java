package com.example.demo.exception;

public class EndingCreditNotFoundException extends RuntimeException {
    
    public EndingCreditNotFoundException(String message) {
        super(message);
    }
    
    public EndingCreditNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
} 