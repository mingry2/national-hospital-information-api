# <center>" 전국 병/의원 정보 조회 api "</center>

---

## ✔ Swagger(spring api doc)
👉👉 http://ec2-54-180-237-143.ap-northeast-2.compute.amazonaws.com:8080/swagger-ui/index.html

※ swagger를 통해 api 문서 자동화

📢 회원가입 이외 모두 jwt를 이용해 보안을 하기 때문에 아래의 방법으로 토큰을 입력해주세요-!

![img_1.png](img/img_1.png)

1. 로그인 API 호출을 위해 누르고 'Try it out' 클릭

![img.png](img/img.png)

2. Request body에 username : guest / password : guest 입력 후 'Execute' 클릭

![img_2.png](img/img_2.png)

3. 응답 헤더에 주어지는 jwt 토큰을 복사(Bearer 이후 영문숫자조합만 복사)

![img_3.png](img/img_3.png)

4. Swagger 최상단으로 올라가 우측 'Authorize' 클릭
5. Bearer 이후 영문숫자조합을 Value 빈칸에 붙여넣기 후 Authorize 클릭

![img_4.png](img/img_4.png)

6. 'Close' 클릭 후 화면을 나간 뒤 원하는 API 호출하여 사용할 수 있습니다.(로그인된 상태 10분 유지)

## ✔ 프로젝트를 통한 학습 목표
1. Spring Data JPA를 통한 CRUD 구현 
2. Spring Security + jwt를 이용하여 보안 강화 
3. DDD(도메인 주도 개발) 설계 
4. 인터페이스 / 구현체를 통한 객체지향 & 다형성 
5. @RestControllerAdvice, @ExceptionHandler 이용한 공통 예외 처리 
6. apache api 라이브러리로 엑셀 데이터 DB insert(대용량 데이터 처리)
7. QueryDSL - 동적쿼리 구현(검색 기능 추가)
8. Swagger(Spring Doc)을 활용하여 API 문서 자동화
9. JUnit5 + Mock 테스트 코드 작성
10. Github Actions, Docker, EC2를 사용하여 CI/CD 구현

## ✔ 프로젝트 설명
* 전국 병/의원 정보 조회 & 검색(병원명) & 등록
* 병/의원에 대한 리뷰 작성
* 리뷰에 대한 댓글 작성 / 리뷰, 댓글에 '좋아요' 클릭으로 유저간 커뮤니티 가능

## ✔ 개발 환경
* FrameWork - Spring Boot 3.4.2
* Language - JDK 21
* IDE - IntelliJ
* DB - MySQL
* connector - Spring Data JPA
* Build - Gradle 8.12.1

## ✔ ERD
![national_hospital_information_api-클래스다이어그램.png](img/national_hospital_information_api-%ED%81%B4%EB%9E%98%EC%8A%A4%EB%8B%A4%EC%9D%B4%EC%96%B4%EA%B7%B8%EB%9E%A8.png)

## ✔ 학습 내용
### 1. RESTful한 API 설계
- HTTP 메서드를 활용하여 RESTful한 api 설계
- DELETE(삭제)가 아닌 PATCH(부분 업데이트)를 사용하여 삭제된 데이터도 보존
- 명확한 리소스 경로를 설계하고 HTTP 메서드를 통해 동작 방식을 구분
- 스프링 프레임워크에서 제공하는 HTTP Mapping 애노테이션 사용
- @RestController를 사용하여 클라이언트에게 JSON 데이터로 응답
- ResponseEntity를 통해 명확한 응답 상태값을 반환하고, 성공/실패 시 Response라는 공통 포멧을 사용하여 간단한 결과값을 담아 반환

### 2. DTO(Data Transfer Object)를 사용하여 계층 간 데이터 전송
- 엔티티를 컨트롤러나 서비스 계층에서 사용하지 않고, DTO를 사용하여 데이터 전송
  → 엔티티는 DB랑만 소통할 수 있게하여 외부에 노출되는 것을 방지함(보안 강화)
- DTO에 필요한 데이터만 담아서 전송
  → 엔티티에 담긴 모든 데이터를 전송하는게 아닌 요청 및 응답 시 필요한 데이터만 담아서 민감한 데이터 노출을 방지하고, 데이터 전송 시 트래픽을 절감시켜줌
- 자바의 record 클래스를 이용하여 DTO 생성
  → DTO의 불변성을 보장하고, 생성자나 getter 등 코드 간결화

### 3. @ExceptionHandler를 이용한 공통 예외처리
- ErrorCdoe를 작성하여 통일된 에러 응답값 반환
- @RestControllerAdvice를 함께 사용하여 RestController에서 발생하는 예외를 감지하여 @ExceptionHandler 형식에 맞춰 반환될 수 있도록 처리
- 모든 에러를 GlobalException이 발생되도록 처리

### 4. service, repository 인터페이스 생성
- service와 repository를 인터페이스로 생성하고, 구현체를 만들어 사용
- 데이터베이스가 변경되거나 서비스 수정 필요할 때 다형성을 이용하여 유지보수를 쉽게할 수 있음

### 5. JPA, Spring Data JPA를 이용한 CRUD
- 등록, 수정, 삭제를 Spring Data JPA를 통해 query 작성 없이 메서드 호출만으로 구현
- JPA Auditing을 사용하여 데이터 변경 이력 추적 기능 추가(생성일, 수정일, 삭제일, 생성자, 수정자)
- 데이터 수정 시 JPA 영속성 컨텍스트의 더티체킹 기능을 사용하여 update 메서드 호출 없이 데이터 수정
- Spring Data JPA Pageable을 이용하여 페이징 처리

### 6. QueryDSL을 사용하여 검색 기능 추가
- 동적 쿼리가 필요한 검색 기능은 QueryDSL을 사용하여 구현

### 7. apache poi 라이브러리
- 엑셀 파일에서 필요한 데이터를 데이터베이스에 삽입

### 8. spring security를 사용하여 로그인 기능 구현
- 세션기능은 비활성화하고 jwt 발급으로 인증 구현
- 로그인 성공하면 응답 헤더에 jwt 담아 클라이언트에 전송하고, 서버에 요청 시 요청 헤더에 해당 토큰을 담아 요청

### 9. Github Actions를 사용하여 ci/cd 구현
- Github Actions 파이프라인을 통해 빌드, 배포 진행
- 배포는 AWS EC2 서버에 Docker를 설치하여 컨테이너로 띄움
- 웹애플리케이션과 디비 컨테이너를 띄울 땐 Docker Compose를 사용하여 컨테이너들을 한번에 관리

### 10. Swagger를 사용하여 api 문서화, api 테스트, api 설계
- 도메인별 api를 Tag로 묶어 한번에 api를 확인할 수 있고, 각 api에 설명글을 달아 어떤 기능을 하는지 알 수 있게 swagger 추가 옵션을 사용

### 11. SpringbootTest, JUnit5와 Mock을 사용한 테스트 코드 작성
- 서비스는 Mock을 사용하여 테스트 코드 작성(서비스는 컨트롤러와 리포지토리 중간에서 호출하는 역할을 주로하기 때문에 가짜 객체를 통해 요청과 반환에대해 테스트 진행)
- 컨트롤러는 MockMvc를 사용하여 api 호출 테스트 진행
- 리포지토리는 Testcontainers를 사용하여 테스트용 mysql 디비를 도커 컨테이너로 띄워 직접 테스트를 직행