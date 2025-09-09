package com.example.demo.dto;

import com.example.demo.domain.entity.EndingCredit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EndingCreditResponse {
    
    private Long id;
    private Long conversationId;
    private String content;
    private LocalDateTime createdAt;
    
    public static EndingCreditResponse from(EndingCredit endingCredit) {
        return EndingCreditResponse.builder()
                .id(endingCredit.getId())
                .conversationId(endingCredit.getConversationId())
                .content(endingCredit.getContent())
                .createdAt(endingCredit.getCreatedAt())
                .build();
    }
} 