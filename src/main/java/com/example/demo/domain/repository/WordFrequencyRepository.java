package com.example.demo.domain.repository;

import com.example.demo.domain.entity.WordFrequency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WordFrequencyRepository extends JpaRepository<WordFrequency, Long> {
    
    // 빈도순으로 전체 단어 조회 (Word Cloud용)
    List<WordFrequency> findAllByOrderByFrequencyDesc();
    
    // 특정 단어 조회
    Optional<WordFrequency> findByWord(String word);
} 