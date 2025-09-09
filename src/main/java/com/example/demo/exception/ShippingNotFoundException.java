package com.example.demo.exception;

public class ShippingNotFoundException extends RuntimeException {
    
    public ShippingNotFoundException(String shippingId) {
        super("송장번호 " + shippingId + "에 해당하는 배송 정보를 찾을 수 없습니다.");
    }
    
    public ShippingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
} 