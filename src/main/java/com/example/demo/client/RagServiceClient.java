package com.example.demo.client;

import com.example.demo.dto.RagSummaryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class RagServiceClient {

    private final WebClient webClient;

    @Value("${rag.service.url:http://localhost:8000}")
    private String ragServiceUrl;

    /**
     * RAG 서비스에서 대화 요약을 가져옵니다.
     */
    public RagSummaryResponse getSummaryFromRag(String conversationId) {
        try {
            log.info("RAG 서비스에서 대화 요약 요청: conversationId={}", conversationId);
            
            // RAG 서비스 API 호출
            Map<String, String> requestBody = Map.of("conversation_id", conversationId);
            
            RagSummaryResponse response = webClient.post()
                    .uri(ragServiceUrl + "/rag/summarize")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(RagSummaryResponse.class)
                    .timeout(Duration.ofSeconds(30))
                    .block();
            
            log.info("RAG 서비스 응답 성공: conversationId={}", conversationId);
            return response;
            
        } catch (WebClientResponseException e) {
            log.error("RAG 서비스 호출 실패: conversationId={}, status={}, error={}", 
                     conversationId, e.getStatusCode(), e.getMessage());
            throw new RuntimeException("RAG 서비스에서 요약을 가져올 수 없습니다: " + e.getMessage(), e);
            
        } catch (Exception e) {
            log.error("RAG 서비스 호출 중 예외 발생: conversationId={}, error={}", 
                     conversationId, e.getMessage());
            throw new RuntimeException("RAG 서비스 호출 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }
} 