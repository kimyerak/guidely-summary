package com.example.demo.service;

import com.example.demo.domain.entity.EndingCredit;
import com.example.demo.domain.entity.WordFrequency;
import com.example.demo.domain.repository.EndingCreditRepository;
import com.example.demo.domain.repository.WordFrequencyRepository;
import com.example.demo.dto.EndingCreditRequest;
import com.example.demo.dto.EndingCreditResponse;
import com.example.demo.dto.WordCloudResponse;
import com.example.demo.exception.EndingCreditNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SummaryStatisticsService {

    private final EndingCreditRepository endingCreditRepository;
    private final WordFrequencyRepository wordFrequencyRepository;

    // 한국어 및 영어 단어 패턴
    private static final Pattern WORD_PATTERN = Pattern.compile("^[가-힣a-zA-Z]{2,}$");
    
    // 불용어 목록 (간단한 예시)
    private static final Set<String> STOP_WORDS = Set.of(
        "이", "그", "저", "것", "수", "등", "및", "또는", "하지만", "그러나", "따라서",
        "the", "a", "an", "and", "or", "but", "in", "on", "at", "to", "for", "of", "with"
    );

    /**
     * EndingCredits 생성 및 전체 단어 빈도 재계산
     */
    @Transactional
    public List<EndingCreditResponse> createEndingCredits(EndingCreditRequest request) {
        Long conversationId = request.getConversationId();
        
        // 기존 데이터 삭제 (재요청 시)
        List<EndingCredit> existingCredits = endingCreditRepository.findByConversationId(conversationId);
        if (!existingCredits.isEmpty()) {
            endingCreditRepository.deleteAll(existingCredits);
        }
        
        // 각 content를 개별 EndingCredit으로 저장
        List<EndingCredit> savedCredits = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        
        for (String content : request.getContents()) {
            EndingCredit endingCredit = EndingCredit.builder()
                    .conversationId(conversationId)
                    .content(content)
                    .createdAt(now)
                    .build();
            
            savedCredits.add(endingCreditRepository.save(endingCredit));
        }
        
        // 전체 단어 빈도 재계산
        recalculateAllWordFrequency();
        
        return savedCredits.stream()
                .map(EndingCreditResponse::from)
                .collect(Collectors.toList());
    }

    /**
     * Conversation ID로 EndingCredits 조회
     */
    public List<EndingCreditResponse> getEndingCreditsByConversationId(Long conversationId) {
        List<EndingCredit> endingCredits = endingCreditRepository.findByConversationId(conversationId);
        
        if (endingCredits.isEmpty()) {
            throw new EndingCreditNotFoundException("EndingCredits not found for conversation: " + conversationId);
        }
        
        return endingCredits.stream()
                .map(EndingCreditResponse::from)
                .collect(Collectors.toList());
    }

    /**
     * 전체 단어 빈도 조회 (Word Cloud용)
     */
    public List<WordCloudResponse> getAllWordFrequency() {
        List<WordFrequency> wordFrequencies = wordFrequencyRepository.findAllByOrderByFrequencyDesc();
        
        return wordFrequencies.stream()
                .map(wf -> WordCloudResponse.of(wf.getWord(), wf.getFrequency()))
                .collect(Collectors.toList());
    }

    /**
     * 전체 단어 빈도 재계산
     */
    @Transactional
    public void recalculateAllWordFrequency() {
        try {
            // 기존 단어 빈도 데이터 모두 삭제하고 즉시 커밋
            wordFrequencyRepository.deleteAll();
            wordFrequencyRepository.flush();
            
            // 모든 EndingCredit의 content를 가져와서 합치기
            List<EndingCredit> allCredits = endingCreditRepository.findAll();
            
            if (allCredits.isEmpty()) {
                return;
            }
            
            String combinedContent = allCredits.stream()
                    .map(EndingCredit::getContent)
                    .filter(content -> content != null && !content.trim().isEmpty())
                    .collect(Collectors.joining(" "));
            
            if (combinedContent.trim().isEmpty()) {
                return;
            }
            
            Map<String, Integer> wordCount = analyzeWordFrequency(combinedContent);

            // WordFrequency 엔티티로 변환 및 저장
            if (!wordCount.isEmpty()) {
                // 기존 데이터와 중복되지 않도록 확인 후 저장
                for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
                    String word = entry.getKey();
                    Integer frequency = entry.getValue();
                    
                    // 중복 체크 후 저장 또는 업데이트
                    WordFrequency existingWord = wordFrequencyRepository.findByWord(word).orElse(null);
                    
                    if (existingWord != null) {
                        // 기존 단어가 있으면 빈도수 업데이트
                        existingWord = WordFrequency.builder()
                                .id(existingWord.getId())
                                .word(word)
                                .frequency(frequency)
                                .createdAt(existingWord.getCreatedAt())
                                .build();
                        wordFrequencyRepository.save(existingWord);
                    } else {
                        // 새로운 단어면 생성
                        WordFrequency newWord = WordFrequency.builder()
                                .word(word)
                                .frequency(frequency)
                                .createdAt(LocalDateTime.now())
                                .build();
                        wordFrequencyRepository.save(newWord);
                    }
                }
            }
        } catch (Exception e) {
            // 로그 출력 후 예외 전파
            System.err.println("단어 빈도 재계산 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("단어 빈도 재계산 실패", e);
        }
    }

    /**
     * 텍스트에서 단어 빈도 분석
     */
    private Map<String, Integer> analyzeWordFrequency(String text) {
        if (text == null || text.trim().isEmpty()) {
            return new HashMap<>();
        }

        Map<String, Integer> wordCount = new HashMap<>();
        
        // 텍스트를 공백과 구두점으로 분리
        String[] words = text.replaceAll("[^가-힣a-zA-Z0-9\\s]", " ")
                              .toLowerCase()
                              .split("\\s+");

        for (String word : words) {
            word = word.trim();
            
            // 유효한 단어인지 확인 (패턴 매칭, 불용어 제외)
            if (WORD_PATTERN.matcher(word).matches() && !STOP_WORDS.contains(word)) {
                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            }
        }

        return wordCount;
    }
} 