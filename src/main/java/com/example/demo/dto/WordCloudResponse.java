package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WordCloudResponse {
    
    private String word;
    private Integer frequency;
    
    public static WordCloudResponse of(String word, Integer frequency) {
        return WordCloudResponse.builder()
                .word(word)
                .frequency(frequency)
                .build();
    }
} 