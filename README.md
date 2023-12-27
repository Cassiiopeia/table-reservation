# 매장 테이블 예약 서비스
- 식당이나 점포를 이용하기 전에 미리 예약을 하여 편하게 식당/점포를 이용할 수 있는 서비스 개발

## 개발환경
---
- JDK 17
- Spring Boot, Gradle
- MariaDB
  
## 필요 기능
---
### 회원 가입 기능
- [ ]매장의 점장은 예약 서비스 앱에 상점을 등록한다.(매장 명, 상점위치, 상점 설명)
- [ ] 회원가입기능

### 매장 등록/수정/삭제 
- [ ] 매장을 등록하기 위해서는 파트너 회원 가입이 되어야 한다.
(따로, 승인 조건은 없으며 가입 후 바로 이용 가능)
- [ ] 매장 등록 기능 구현 
- [ ] 매장 수정 기능 구현 
- [ ] 매장 삭제 기능 구현 

### 예약진행
- [ ] 매장 이용자는 앱을 통해서 매장을 검색하고 상세 정보를 확인한다.

- [ ] 매장의 상세 정보를 보고, 예약을 진행한다
(예약을 진행하기 위해서는 회원 가입이 필수적으로 이루어 져야 함.)

- [ ] 서비스를 통해서 예약한 이후에, 예약 10분전에 도착하여 키오스크를 통해서 방문 확인 진행
- [ ] 도착 확인 기능 구현 (유효성 필수 체크)

### 리뷰작성
- [ ] 예약및 사용이후에 리뷰 작성가능
- [ ] 리뷰의 경우, 수정은 리뷰 작성자만, 삭제 권한은 리뷰를 작성한 사람과 매장의 관리자(점장등)만 삭제할 수 있습니다.
