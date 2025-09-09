package com.example.demo.exception;

import java.util.List;
import java.util.Map;

public class ValidationException extends RuntimeException {
    
    private final List<String> details;
    private final Map<String, String> fieldErrors;
    
    public ValidationException(String message) {
        super(message);
        this.details = null;
        this.fieldErrors = null;
    }
    
    public ValidationException(String message, List<String> details) {
        super(message);
        this.details = details;
        this.fieldErrors = null;
    }
    
    public ValidationException(String message, Map<String, String> fieldErrors) {
        super(message);
        this.details = null;
        this.fieldErrors = fieldErrors;
    }
    
    public List<String> getDetails() {
        return details;
    }
    
    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }
} 