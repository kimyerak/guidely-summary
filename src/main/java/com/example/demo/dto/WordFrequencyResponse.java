package com.example.demo.dto;

import com.example.demo.domain.entity.WordFrequency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WordFrequencyResponse {
    
    private String word;
    private Integer frequency;
    private LocalDateTime createdAt;
    
    public static WordFrequencyResponse from(WordFrequency wordFrequency) {
        return WordFrequencyResponse.builder()
                .word(wordFrequency.getWord())
                .frequency(wordFrequency.getFrequency())
                .createdAt(wordFrequency.getCreatedAt())
                .build();
    }
} 