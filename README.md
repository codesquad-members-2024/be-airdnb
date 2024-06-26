# **마스터즈 2024-BE 팀 프로젝트 : 숙박앱**

## 👫 Team1 소개 👫

|                                    개발(BE)                                     |                                    개발(BE)                                     |
| :-----------------------------------------------------------------------------: | :-----------------------------------------------------------------------------: |
| <img width="160px" src="https://avatars.githubusercontent.com/u/87180146?v=4"/> | <img width="160px" src="https://avatars.githubusercontent.com/u/85686722?v=4"/> |
|                    [@Miensoap](https://github.com/Miensoap)                     |                   [@soyesenna](https://github.com/soyesenna)                    |
|                                      Soap                                       |                                      Senna                                      |

## Notion

[노션 페이지](https://fallacious-cadet-384.notion.site/AirBnB-fd3159ffb3714d4a953011849346278f?pvs=4)

---

# 🎈 활동 기록 🎈

_각 주차 제목을 누르면 기록 위키로 이동합니다!_

## 😊 [1주차](https://github.com/CodeSquad-Airbnb-Team07/be-airbnb/wiki/1%EC%A3%BC%EC%B0%A8-%EA%B8%B0%EB%A1%9D)

### 요약

- 주변 숙소 조회 API
- 예약 가능한 주변 상품 조회 API
- 숙소 등록 , 상세 조회, 삭제 API
- 상품 등록 API
- 예약 전 금액 확인 API

- 깃허브 , 구글 OAuth 로그인 구현
- 인증 후 JWT 토큰에 담기 현재 유저 정보 사용 가능

#### 고민

- Point , LocalDate , LocalDateTime : Serializer Deserializer 오류나서 직접 구현
- 프론트 페이지에서 OAuth 로그인 구현 안되는중 😭😭😭
- 서버 로그를 ec2 접속하지 않고 보고싶어요

#### 기타

- github action + docker + ec2 사용해 자동 [배포중](https://squadbnb.site)
- next -> react로 변경

---

## 😊 [2주차](https://github.com/CodeSquad-Airbnb-Team07/be-airbnb/wiki/2%EC%A3%BC%EC%B0%A8-%EA%B8%B0%EB%A1%9D)

### 요약

- 예약, 위시리스트 , 리뷰 API
- SpringDoc
- JWT 토큰에 담는 유저 정보 수정
- 패키지 구조 수정

#### 고민

- 패키지 구조를 도메인별로 사용하고 있었는데, 레이어를 명확히 구분하기 위해 수정 시도중이에요
- 테스트 코드가 필요할 것 같아요
- 수정을 구현할 때 Entity에 setter를 만들어 dirty checking을 사용할지, PUT 으로 모든 정보를 한 번에 수정할지, ... ... ...

#### 결정

- 패키지 구조 수정
- Custom Exception 구조화 , GlobalExceptionHadler에서 공통 처리
- Api 공통 응답 객체는 사용하지 않기로 결정
- schema.sql 작성 -> ddl-auto 기능 validate / none 사용

_이미지를 클릭하면 위키로 이동합니다!_

[<img src="https://github.com/codesquad-members-2024/be-airdnb/assets/87180146/ae18776e-076f-41a4-9ed7-b457f9018153" width="50%" height="50%">](https://github.com/CodeSquad-Airbnb-Team07/be-airbnb/wiki/%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%B2%A0%EC%9D%B4%EC%8A%A4-%EC%8A%A4%ED%82%A4%EB%A7%88)

#### 기능

- 월별 예약 가능 일자 조회 API
- 내가 등록한 숙소 조회
- 내 숙소에 대한 예약 조회
- 내 예약 조회 (유저)
- 예약 단독 상세 조회
- 예약 이용 완료
- 요금 그래프
- 예약 수정
- 댓글 내용 수정
  <br>

#### 기타

- config 분리
- allow url Set으로 관리
- validation 일부 적용 , 예외 처리
- 응답 DTO 추가

[nginx 이슈](https://github.com/CodeSquad-Airbnb-Team07/be-airbnb/issues/4) 해결!

# 👉👉 https://squadbnb.site/api/docs

---

## 😊 [3주차](https://github.com/CodeSquad-Airbnb-Team07/be-airbnb/wiki/3%EC%A3%BC%EC%B0%A8-%EA%B8%B0%EB%A1%9D)

### 수정사항

- BookingService 기능별로 분리
- BookingService -> BookingAuthService, BookingInquiryService, BookingManageService, BookingPriceService 로 분리
- 클라이언트의 권한을 검사하는 로직을 Controller -> Service로 이전
- Authenticated AOP 예외 메시지 자세하게 수정

- AOP 로 TokenUserInfo가 아닌 UserEntity 입력하도록 수정
- 필터링 기능
- S3 Presigned url 이용한 사진 업로드
- 공공 API 이용해 더미 데이터 추가

#### 테스트를 위한 초기 세팅 ⚙️

- RestAssured 사용한 유틸 클래스 추가
- DB 초기화 위한 클래스 추가
- FixtureMonkey : key duplicate 문제 해결 위해 request dto에서 Position 타입 사용하지 않도록 수정
- 테스트 db 설정 추가
- Request Dto에 validation 추가
- 매 요청마다 JWT를 헤더에 주도록 TestConfig, Request 클래스 수정

#### 완성한 테스트코드 👍

- 예약 생성
- 예약 요금 조회
- 위시 리스트 API
- 위시 리스트 추가
- 위시 리스트 조회
- 위시 리스트 삭제

#### 고민

- 특정 도메인에 대한 API 통합 테스트 진행할 때 다른 엔티티의 정보가 필요할 경우 어떤 방식이 적절할지
  1.  직접 더미 데이터를 테스트 진행하기 전에 DB에 임시로 추가 후 사용한 다음, 테스트 완료 후 DB의 데이터 초기화
  - 만약, DB를 직접 다룰 때 PersistenceContext는 어떻게 관리하는지??
  2. 다른 도메인의 서비스를 API 통합 테스트 클래스가 의존하고 있어서 해당 서비스의 메서드를 이용하여 DB에 추가

#### `24-06-18` 페어 프로그래밍 : 예약 서비스 관련 리팩터링

---

## 😊 [4주차](https://github.com/CodeSquad-Airbnb-Team07/be-airbnb/wiki/4%EC%A3%BC%EC%B0%A8-%EA%B8%B0%EB%A1%9D)

### 요약

- 프론트 작업 중 발견한 오류 수정
- CORS 오류 해결
- List나 Long 타입을 응답 -> DTO 클래스로 감싸서 응답

#### 고민

- 깊은 depth의 API 응답을 프론트엔드 코드 작성 중 사용하다 보니
  - `data.accommodation.address.logitude` 같이 depth에 따라 사용에 불편을 느낄 수 있겠다는 생각!
  - 이러한 점을 고려해 펼쳐 응답하는게 어떨지?

#### 교훈

- 리스트를 직접 응답하기보단 응답 DTO를 사용한다는 리뷰를 반영
  - totalIncome 과 같은 정보가 요구사항에 추가되어도 별 문제 없이 유연하게 추가할 수 있겠다! " 라는 생각!
  - 동시에 API 명세에 변경이 생기는 작업을 하다보니, 프론트엔드 코드에도 영향을 미치는 것을 경험!

---
