# 베이스 이미지 설정 (JDK 21 사용)
FROM eclipse-temurin:21-jdk

# 작업 디렉터리 생성
WORKDIR /app

# Gradle 빌드 결과 JAR 파일 복사
COPY build/libs/*.jar app.jar

# 컨테이너 실행 시 JAR 파일 실행
CMD ["java", "-jar", "app.jar"]