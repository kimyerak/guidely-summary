package com.example.demo.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "word_frequency")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WordFrequency {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "word", nullable = false, unique = true)
    private String word;
    
    @Column(name = "frequency", nullable = false)
    private Integer frequency;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    // 빈도수 증가 메서드
    public void incrementFrequency() {
        this.frequency++;
    }
} 