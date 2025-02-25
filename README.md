# <center>" 전국 병/의원 정보 조회 api "</center>

---

## ✔ Swagger(spring api doc)
👉 http://ec2-3-38-95-120.ap-northeast-2.compute.amazonaws.com:8080/swagger-ui/index.html

※ swagger를 통해 api 문서 자동화

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
![national_hospital_information_api-클래스다이어그램.png](national_hospital_information_api-%ED%81%B4%EB%9E%98%EC%8A%A4%EB%8B%A4%EC%9D%B4%EC%96%B4%EA%B7%B8%EB%9E%A8.png)

## ✔ 학습 내용
### 1. DDD(도메인 주도 개발)
내 프로젝트는 규모가 작고, 도메인별로 복잡한 서비스 로직이 많지 않기 때문에 꼭 **도메인 주도 개발(DDD)**을 적용할 필요는 없었다. 하지만 최근 MSA 아키텍처, 더 나아가 모듈러 모놀리식 아키텍처가 점점 대중화되는 흐름 속에서 한 번쯤 경험해보고 싶었다.

아직 프로젝트 경험이 부족한 나로서는, 오히려 작은 규모이기 때문에 부담 없이 DDD를 시도해볼 수 있었다. 도메인 주도 개발 방식으로 설계하면, 도메인별·모듈별로 나뉜 구조 덕분에 MSA나 모듈러 모놀리식 아키텍처를 도입하기가 훨씬 쉬워진다. 또한, 모듈 간 캡슐화를 통해 독립적인 개발과 유지보수가 가능해지는 장점도 있다.

뿐만 아니라, 자주 호출되는 도메인의 경우 성능 최적화를 고려하거나, 더 좋은 컴퓨팅 환경을 제공함으로써 서버 부하를 분산할 수도 있다. 이렇게 하면 특정 도메인에 요청이 몰릴 때 서버가 다운되는 문제를 예방할 수 있다.

하지만, 아무리 작은 규모의 프로젝트라고 해도 공통적으로 사용되는 Security, Exception 등의 위치를 선정하는 문제나, 애그리거트 & 루트 개념을 적용하는 과정에서 시행착오가 많았다. 특히, 어떤 객체를 애그리거트 루트로 설정해야 할지 고민하는 과정이 많았고, 각 모듈의 역할과 경계를 명확하게 나누는 것이 쉽지 않았다.

아직은 이 정도만 이해한 상태고, 실제로 실무에서 적용해보진 않았지만, 나중에 실무에서 꼭 한 번 제대로 경험해보고 싶다.


### 2. Spring Security + jwt
부트캠프에서 진행한 프로젝트에서 Spring Security + JWT를 사용하여 보안을 관리했었다. 그때는 Security의 복잡한 필터 체인이나 토큰을 생성하고 응답에 담아주는 방식, 그리고 토큰을 어떻게 인증하는지에 대해 잘 이해되지 않았었다.
하지만 이번 프로젝트를 진행하면서 혼자서 Security의 흐름을 파악하며 구현해보니, 그때보다 이해가 조금 더 깊어졌다. 물론, 내가 이해한 것보다 더 많은 내용들이 있겠지만, 기본적인 흐름은 꽤 명확하게 파악할 수 있었다.

Spring Security는 기본적으로 세션 방식을 사용하지만, JWT를 이용하려면 세션을 비활성화하고, 필터 체인을 커스텀하여 JWT를 생성하고 인증하는 과정이 필요하다.
특히, UsernamePasswordAuthenticationFilter를 커스텀하여 만든 LoginFilter로, 로그인 정보가 담긴 내용을 authenticationManager에 넘기면서 인증을 시작한다.
기본적인 흐름은 검색하고 경험했던것을 살려 적용했지만, swagger를 적용하면서 security 필터체인이 계속 인증을 요구하여 swagger 도입에 불편한 점이 많았다.
우여곡절 끝에 인증 없이 사용하는 방법을 알고 적용했지만, 앞으로 규모가 커지면서 security와 충돌하는 상황이 계속 발생될 것 같다.

연동방식을 조금 더 이해하여 추후 충돌이 발생해도 어떤 부분을 수정하면 되는지 단번에 알 수 있으면 좋겠다.
