package com.example.demo.exception;

public class DuplicateShippingException extends RuntimeException {
    
    public DuplicateShippingException(String shippingId) {
        super("이미 존재하는 송장번호입니다: " + shippingId);
    }
    
    public DuplicateShippingException(String message, Throwable cause) {
        super(message, cause);
    }
} 