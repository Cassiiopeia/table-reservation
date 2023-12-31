# 매장 테이블 예약 서비스
- 식당이나 점포를 이용하기 전에 미리 예약을 하여 편하게 식당/점포를 이용할 수 있는 서비스 개발

## 개발환경
---
- JDK 17
- Spring Boot, Gradle
- MariaDB

## Model 설명
- Store : 매장
- Customer : 소비자 회원
- Partner : 매장관리자 회원
- Reservation : 예약
- Review : 리뷰
  
## 필요 기능
---
### 부가기능
- [X] 커스텀 에러 구현
- [ ] 비밀번호 암호화
- [X] 로그인시 Token 발급 구현

### 토큰 발급 및 필터
- [X] 토큰 생성
- [X] 토큰 유효성 검사
- [X] Token 필터 생성
  - 모든 URI에 token이 유효한지 체크
  - URI에 "partner", "customer" 가 들어가면 Token의 UserType과 일치하는지 체크
  - Token 필터 예외 : URI에 "signin", "signup", "kiosk" 가 들어가면 Token을 사용하지않으므로 토큰이 유효한지 확인하지않는다

### 회원 가입 기능
- [X] 회원 가입 기능 ( Partner, Customer )
- [X] 토큰 부여 방식 ( Partner, Customer )


### 매장 등록/수정/삭제/조회
- [X] 매장을 등록하기 위해서는 파트너 회원 가입이 되어야 한다.
  - (따로, 승인 조건은 없으며 가입 후 바로 이용 가능)
- [X] 매장 등록 기능 구현 (Partner전용)
- [X] 매장 수정 기능 구현 (Partner전용)
- [X] 매장 삭제 기능 구현 (Partner전용)
- [X] 매장 조회 기능 (Partner전용-본인매장 검색)
- [X] 매장 조회 기늗 (Customer전용: 검색어 -> 매장이름으로 검색)
      - Customer는 앱을 통해서 매장을 검색하고 상세 정보를 확인할수 있다.

### 예약진행
- [X] 예약 생성
  - 매장의 예약가능여부확인
  - 예약 생성시 ReservationStatus는 Request 이다.
- [X] 예약 변경 - 예약상태가 Cancelled, Confirmed 이면 변경불가능
- [X] 예약 취소 - 예약상태가 Confirmed이면 취소불가능
- [X] Customer의 자신의 예약조회
- [X] Store에 대한 예약조희 (Partner 소유의 Store 조회가능)
- [X] 예약승인 (Partner)

### 키오스크
- [X] 키오스크를 통해서 방문 확인 진행 ( 예약시간 -10분 ~ +60분 사이에 방문 확인 가능 )
- [X] 도착 확인 기능 구현
  - 유효성 체크 - ReservationCode 를 사용하여 도착 확인
  - 해당 URI에는 Token을 사용하지않는다 (Filter 예외처리)
  - 예약상태는 예약승인(Confirmed)에서 방문확인(Visited)로 변경된다.

### 리뷰작성
- [X] 리뷰 생성 (매장을 방문한 Customer만 리뷰 작성가능)
  - 리뷰 중복 방지: 한리뷰에 대한 여러 리뷰 작성방지
  - 예약 상태 중복확인: 예약상태가 VISITED 상태에 있을때만 리뷰 작성가능
  - 예약방문 30일이 지나면 리뷰 작성 불가
  - 방문날짜 검증: 리뷰의 방문 날짜가 실제 예약된 방문날짜와 일치해야함

- [X] 리뷰 수정 (리뷰작성 Customer만 수정가능)
- [X] 리뷰 삭제 (리뷰작성 Customer, 해당 Store의 Partner가 삭제가능)
- [X] 리뷰 조회 ( Customer의 리뷰 조회 : Customer만 조회가능 )
- [X] 리뷰 조회 ( Store의 리뷰 조회 : Store의 Partner만 조회가능 )

### 더 생각해야할점
- [ ] 예약시간이 지나면 자동으로 예약상태가 EXPIRED로 바뀌어야함
- [ ] Store의 Partner의 변경이 있을시 조건 필요
- [ ] api분리 : 여러폴더로 나눠야하는데 어떤식으로 나눠야할지 모르겠다.
