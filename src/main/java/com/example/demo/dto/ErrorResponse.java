package com.example.demo.dto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    
    // 추가 필드들 (선택적 사용)
    private String errorCode;           // 비즈니스 에러 코드 (예: SHIPPING_NOT_FOUND, VALIDATION_ERROR)
    private List<String> details;       // 상세 에러 목록 (유효성 검증 실패 시)
    private Map<String, String> errors; // 필드별 에러 메시지
    private String traceId;             // 로그 추적 ID
} 