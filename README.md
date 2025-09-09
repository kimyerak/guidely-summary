# Demo Spring Boot Project

## 프로젝트 개요
Spring Boot 3.5.5 기반의 웹 애플리케이션 프로젝트입니다.

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

## 프로젝트 구조
```
demo/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/demo/
│   │   │       └── DemoApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/
│   │       └── templates/
│   └── test/
│       └── java/
├── build.gradle
├── gradlew
├── gradlew.bat
└── settings.gradle
```

## 시작하기

### 사전 요구사항
- Java 17 이상
- Gradle (또는 Gradle Wrapper 사용)

### 프로젝트 실행
```bash
# 프로젝트 디렉토리로 이동
cd demo

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
- **Actuator 엔드포인트**: http://localhost:8080/actuator
- **H2 콘솔**: http://localhost:8080/h2-console

## 주요 기능
- Spring Web (REST API 개발)
- Spring Data JPA (데이터베이스 연동)
- Spring Boot Actuator (모니터링)
- H2 Database (인메모리 데이터베이스)
- MySQL Driver (MySQL 데이터베이스 연동)
- Lombok (코드 간결화)

## 개발 가이드

### 패키지 구조
현재 기본 패키지 구조만 생성되어 있습니다. 필요에 따라 다음과 같은 계층 구조를 추가할 수 있습니다:

```
com.example.demo/
├── controller/     # REST API 컨트롤러
├── service/        # 비즈니스 로직
├── repository/     # 데이터 접근 계층
├── entity/         # JPA 엔티티
├── dto/           # 데이터 전송 객체
└── config/        # 설정 클래스
```

### 데이터베이스 설정
`application.properties` 파일에서 데이터베이스 설정을 변경할 수 있습니다:

```properties
# H2 Database (개발용)
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# MySQL (운영용)
# spring.datasource.url=jdbc:mysql://localhost:3306/demo
# spring.datasource.username=root
# spring.datasource.password=password
# spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

## 테스트
```bash
# 테스트 실행
./gradlew test

# 테스트 커버리지 확인
./gradlew test jacocoTestReport
```

## 배포
```bash
# JAR 파일 생성
./gradlew build

# 생성된 JAR 파일은 build/libs/ 디렉토리에 위치
```

## 라이센스
이 프로젝트는 MIT 라이센스 하에 배포됩니다. 