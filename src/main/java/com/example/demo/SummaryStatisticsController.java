package com.example.demo;

import com.example.demo.dto.EndingCreditRequest;
import com.example.demo.dto.EndingCreditResponse;
import com.example.demo.dto.WordCloudResponse;
import com.example.demo.service.SummaryStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/summary-statistics")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SummaryStatisticsController {

    private final SummaryStatisticsService summaryStatisticsService;

    /**
     * EndingCredits 생성 API (RAG에서 받은 여러 요약을 개별 레코드로 저장)
     * POST /api/v1/summary-statistics/ending-credits
     */
    @PostMapping("/ending-credits")
    public ResponseEntity<List<EndingCreditResponse>> createEndingCredits(
            @RequestBody EndingCreditRequest request) {
        
        List<EndingCreditResponse> responses = summaryStatisticsService.createEndingCredits(request);
        return ResponseEntity.ok(responses);
    }

    /**
     * Conversation ID로 EndingCredits 조회
     * GET /api/v1/summary-statistics/conversations/{conversationId}
     */
    @GetMapping("/conversations/{conversationId}")
    public ResponseEntity<List<EndingCreditResponse>> getEndingCredits(
            @PathVariable Long conversationId) {
        
        List<EndingCreditResponse> responses = summaryStatisticsService
                .getEndingCreditsByConversationId(conversationId);
        
        return ResponseEntity.ok(responses);
    }

    /**
     * 전체 단어 빈도 조회 API (Word Cloud용)
     * GET /api/v1/summary-statistics/word-frequency
     */
    @GetMapping("/word-frequency")
    public ResponseEntity<List<WordCloudResponse>> getAllWordFrequency() {
        
        List<WordCloudResponse> wordFrequencies = summaryStatisticsService.getAllWordFrequency();
        
        return ResponseEntity.ok(wordFrequencies);
    }
} 