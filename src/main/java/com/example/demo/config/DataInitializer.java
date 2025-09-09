package com.example.demo.config;

import com.example.demo.domain.entity.EndingCredit;
import com.example.demo.domain.repository.EndingCreditRepository;
import com.example.demo.service.SummaryStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private EndingCreditRepository endingCreditRepository;
    
    @Autowired
    private SummaryStatisticsService summaryStatisticsService;

    @Override
    public void run(String... args) throws Exception {
        // Mock 데이터 자동 생성 비활성화 - API로 직접 데이터 입력
        // initializeTestData();
        System.out.println("=== Summary-Statistics 서비스 시작 완료 ===");
        System.out.println("프론트엔드에서 다음 플로우로 데이터를 입력해주세요:");
        System.out.println("1. Chat 서비스: PUT /api/conversations/{id}/end");
        System.out.println("2. RAG 서비스: POST /rag/summarize");
        System.out.println("3. 우리 서비스: POST /api/v1/summary-statistics/ending-credits");
        System.out.println("================================");
    }

    // Mock 데이터 생성 메서드 (사용 안함)
    private void initializeTestData() {
        // API를 통한 데이터 입력 방식으로 변경됨
        System.out.println("Mock 데이터 생성이 비활성화되었습니다.");
        System.out.println("API를 통해 데이터를 입력해주세요.");
    }
} 