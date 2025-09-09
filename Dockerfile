# 멀티스테이지 빌드를 위한 빌드 스테이지
FROM eclipse-temurin:17-jdk-alpine AS build

# 작업 디렉토리 설정
WORKDIR /app

# Gradle Wrapper와 build.gradle 파일들 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# 소스 코드 복사
COPY src src

# 실행 권한 부여 및 애플리케이션 빌드
RUN chmod +x ./gradlew
RUN ./gradlew build -x test

# 런타임 스테이지
FROM eclipse-temurin:17-jre-alpine

# 애플리케이션 실행을 위한 사용자 생성
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# 작업 디렉토리 설정
WORKDIR /app

# 빌드 스테이지에서 생성된 JAR 파일 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 포트 노출
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"] 