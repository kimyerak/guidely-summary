# Summary Statistics API

## 프로젝트 개요
**MSA(Microservice Architecture) 기반**의 요약 통계 서비스입니다.
RAG 서비스에서 생성된 요약 데이터를 저장하고 분석하여 워드 클라우드용 단어 빈도 데이터를 제공합니다.

## MSA 아키텍처 특징
- **독립적인 배포**: 다른 서비스와 독립적으로 배포 및 확장 가능
- **서비스 간 통신**: RESTful API를 통한 느슨한 결합
- **데이터베이스 분리**: 각 마이크로서비스별 전용 데이터베이스 사용
- **장애 격리**: 단일 서비스 장애가 전체 시스템에 미치는 영향 최소화

## 기술 스택
- **Java 17** + **Spring Boot 3.5.5**
- **MySQL** (운영) / **H2** (개발)
- **Spring Data JPA** + **Spring WebFlux**
- **Gradle** + **Lombok**

## 서비스 구조
```
📦 Summary Statistics Service
├── 🎯 Controller - API 엔드포인트
├── 🔧 Service - 비즈니스 로직  
├── 💾 Repository - 데이터 접근
├── 🌐 Client - 외부 서비스 연동 (RAG Service)
└── 📊 Entity - 데이터 모델 (WordFrequency, EndingCredit)
```

## 주요 API

| 기능 | Method | Endpoint |
|------|--------|----------|
| 요약 데이터 저장 | `POST` | `/api/v1/summary-statistics/ending-credits` |
| 대화별 요약 조회 | `GET` | `/api/v1/summary-statistics/conversations/{conversationId}` |
| 단어 빈도 조회 | `GET` | `/api/v1/summary-statistics/word-frequency` |

## 빠른 시작

### 실행
```bash
./gradlew bootRun
```

### 빌드
```bash
./gradlew build
java -jar build/libs/demo-0.0.1-SNAPSHOT.jar
```

## 서비스 URL

### 운영 환경
- **API**: https://guidely-summary-statistic-dtfac0dde5a0bmea.koreacentral-01.azurewebsites.net/api/v1/summary-statistics
- **Swagger**: https://guidely-summary-statistic-dtfac0dde5a0bmea.koreacentral-01.azurewebsites.net/swagger-ui.html

### 로컬 환경  
- **API**: http://localhost:8080/api/v1/summary-statistics
- **Swagger**: http://localhost:8080/swagger-ui.html

## 핵심 기능
- 📊 **요약 데이터 저장 및 관리**
- 🔍 **단어 빈도 분석 및 워드클라우드 지원**  
- 🔗 **RAG 서비스와의 연동**
- 🚀 **MSA 기반 독립 배포** 
