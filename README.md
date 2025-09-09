# Summary Statistics API

## 프로젝트 개요
RAG 서비스에서 생성된 요약 데이터를 저장하고 분석하는 Spring Boot 3.5.5 기반의 REST API 서비스입니다.
텍스트 요약 데이터를 통계적으로 분석하고 워드 클라우드 생성을 위한 단어 빈도 데이터를 제공합니다.

## 기술 스택
- **Java**: 17
- **Spring Boot**: 3.5.5
- **Build Tool**: Gradle (Groovy)
- **Database**: 
  - H2 Database (개발/테스트용)
  - MySQL (운영용)
- **ORM**: Spring Data JPA
- **Monitoring**: Spring Boot Actuator
- **Utility**: Lombok
- **HTTP Client**: Spring WebFlux WebClient

## 프로젝트 구조
```
summary-statistics/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/demo/
│   │   │       ├── DemoApplication.java
│   │   │       ├── SummaryStatisticsController.java
│   │   │       ├── client/
│   │   │       │   └── RagServiceClient.java
│   │   │       ├── config/
│   │   │       │   ├── DataInitializer.java
│   │   │       │   └── WebClientConfig.java
│   │   │       ├── domain/
│   │   │       │   ├── entity/
│   │   │       │   │   ├── EndingCredit.java
│   │   │       │   │   └── WordFrequency.java
│   │   │       │   └── repository/
│   │   │       │       ├── EndingCreditRepository.java
│   │   │       │       └── WordFrequencyRepository.java
│   │   │       ├── dto/
│   │   │       │   ├── EndingCreditRequest.java
│   │   │       │   ├── EndingCreditResponse.java
│   │   │       │   ├── ErrorResponse.java
│   │   │       │   ├── RagSummaryResponse.java
│   │   │       │   ├── WordCloudResponse.java
│   │   │       │   └── WordFrequencyResponse.java
│   │   │       ├── exception/
│   │   │       │   ├── DuplicateShippingException.java
│   │   │       │   ├── EndingCreditNotFoundException.java
│   │   │       │   ├── ShippingNotFoundException.java
│   │   │       │   └── ValidationException.java
│   │   │       └── service/
│   │   │           └── SummaryStatisticsService.java
│   │   └── resources/
│   │       ├── application.yml
│   │       ├── static/
│   │       └── templates/
│   └── test/
│       └── java/
├── testHttp/
│   └── summary-statistics.http
├── build.gradle
├── gradlew
├── gradlew.bat
└── settings.gradle
```

## API 엔드포인트

### 1. EndingCredits 생성
RAG 서비스에서 받은 여러 요약을 개별 레코드로 저장합니다.

```http
POST /api/v1/summary-statistics/ending-credits
Content-Type: application/json

{
  "conversationId": 1,
  "summaries": ["요약1", "요약2", "요약3"]
}
```

**Response:**
```json
[
  {
    "id": 1,
    "conversationId": 1,
    "summary": "요약1",
    "createdAt": "2024-01-01T12:00:00"
  },
  {
    "id": 2,
    "conversationId": 1,
    "summary": "요약2",
    "createdAt": "2024-01-01T12:00:01"
  }
]
```

### 2. Conversation별 EndingCredits 조회
특정 대화 ID로 저장된 요약 데이터를 조회합니다.

```http
GET /api/v1/summary-statistics/conversations/{conversationId}
```

**Response:**
```json
[
  {
    "id": 1,
    "conversationId": 1,
    "summary": "요약 내용",
    "createdAt": "2024-01-01T12:00:00"
  }
]
```

### 3. 전체 단어 빈도 조회 (Word Cloud용)
워드 클라우드 생성을 위한 전체 단어 빈도 데이터를 제공합니다.

```http
GET /api/v1/summary-statistics/word-frequency
```

**Response:**
```json
[
  {
    "word": "AI",
    "frequency": 150
  },
  {
    "word": "데이터",
    "frequency": 120
  }
]
```

## 시작하기

### 사전 요구사항
- Java 17 이상
- Gradle (또는 Gradle Wrapper 사용)

### 프로젝트 실행
```bash
# 프로젝트 디렉토리로 이동
cd summary-statistics

# Gradle Wrapper를 사용하여 애플리케이션 실행
./gradlew bootRun

# 또는 Windows에서
gradlew.bat bootRun
```

### 빌드
```bash
# JAR 파일 빌드
./gradlew build

# 빌드된 JAR 파일 실행
java -jar build/libs/demo-0.0.1-SNAPSHOT.jar
```

## 애플리케이션 접속
- **메인 애플리케이션**: http://localhost:8080
- **API 베이스 URL**: http://localhost:8080/api/v1/summary-statistics
- **Actuator 엔드포인트**: http://localhost:8080/actuator
- **H2 콘솔**: http://localhost:8080/h2-console

## 주요 기능
- **텍스트 요약 저장**: RAG 서비스에서 생성된 요약 데이터를 체계적으로 저장
- **대화별 요약 관리**: Conversation ID 기반으로 요약 데이터 그룹화 및 조회
- **단어 빈도 분석**: 저장된 요약 데이터에서 단어 빈도 계산 및 제공
- **워드 클라우드 지원**: 시각화를 위한 단어 빈도 데이터 API 제공
- **RESTful API**: 표준 REST API 설계 원칙 준수
- **CORS 지원**: 웹 애플리케이션에서의 크로스 오리진 요청 허용

## 데이터베이스 설정
`application.yml` 파일에서 데이터베이스 설정을 변경할 수 있습니다:

```yaml
# H2 Database (개발용)
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: sa
    password: 

# MySQL (운영용)
# spring:
#   datasource:
#     url: jdbc:mysql://localhost:3306/summary_statistics
#     username: root
#     password: password
#     driver-class-name: com.mysql.cj.jdbc.Driver
```

## 테스트
```bash
# 테스트 실행
./gradlew test

# 테스트 커버리지 확인
./gradlew test jacocoTestReport
```

## HTTP 테스트
`testHttp/summary-statistics.http` 파일을 사용하여 API를 테스트할 수 있습니다.

## Docker 배포

### Docker Hub 레포지토리
- **Docker Hub**: https://hub.docker.com/repository/docker/yerak213/summary-statistics

### Docker 이미지 빌드 및 실행
```bash
# Docker 이미지 빌드
docker build -t yerak213/summary-statistics:latest .

# Docker 컨테이너 실행
docker run -p 8080:8080 yerak213/summary-statistics:latest

# 또는 Docker Hub에서 이미지 pull 후 실행
docker pull yerak213/summary-statistics:latest
docker run -p 8080:8080 yerak213/summary-statistics:latest
```

### Docker Compose 사용 (권장)
프로젝트 루트에 `docker-compose.yml` 파일이 포함되어 있습니다.

```bash
# Docker Compose로 전체 스택 실행 (MySQL 포함)
docker-compose up -d

# 로그 확인
docker-compose logs -f summary-statistics

# 서비스 중지
docker-compose down

# 볼륨까지 삭제 (데이터베이스 데이터 포함)
docker-compose down -v
```

### 프로덕션 환경 배포
```bash
# 프로덕션용 이미지 빌드 및 푸시
docker build -t yerak213/summary-statistics:v1.0.0 .
docker push yerak213/summary-statistics:v1.0.0

# 프로덕션 환경에서 실행
docker run -d \
  --name summary-statistics \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://your-mysql-host:3306/summary_statistics \
  -e SPRING_DATASOURCE_USERNAME=your-username \
  -e SPRING_DATASOURCE_PASSWORD=your-password \
  yerak213/summary-statistics:v1.0.0
```

## 로컬 배포
```bash
# JAR 파일 생성
./gradlew build

# 생성된 JAR 파일은 build/libs/ 디렉토리에 위치
java -jar build/libs/demo-0.0.1-SNAPSHOT.jar
```

## 라이센스
이 프로젝트는 MIT 라이센스 하에 배포됩니다. 