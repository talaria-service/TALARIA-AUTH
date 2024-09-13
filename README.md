# 🪙 TALARIA_AUTH

한 명의 판매자, 구매자가 서비스의 사용자들에게서 편리하게 금을 구매하고, 판매할 수 있는 TALARIA 서비스의 인증서버입니다. 

# 📑 목차
##### [Quick Start](#quick-start)
##### [1. 프로젝트 개요](#1-프로젝트-개요)
 - [⚙️기술 스택](#️-기술-스택)
 - [✔️ 요구사항](#️-요구사항)
##### [2. 프로젝트 관리](#2-프로젝트-관리)
 - [🗓️ 일정(2024.08.04~2024.08.11)](#️-일정2024080420240811)
##### [3. 기술 문서](#3-기술-문서)
 - [📄 API 명세서](#-api-명세서)
##### [4. 기능 구현](#4-기능-구현)
 - [⭐ 사용자 인증 시스템](#-사용자-인증-시스템)
 - [⭐ gRPC 통신을 이용한 JWT 인가 시스템](#-grpc-통신을-이용한-jwt-인가-시스템)
##### [5. 트러블 슈팅](#5-트러블-슈팅)

## Quick Start

1. MariaDB, Redis의 설치 및 실행이 필요합니다.
2. 원하는 위치에 프로젝트를 Clone 합니다.
3. **src\main\resources** 경로 아래에 전달드린 파일 중 **talaria_auth** 폴더에 있는 **application.properties** 파일을 넣어줍니다.
4. 터미널에서 아래 명령어를 차례대로 실행해줍니다. </br>

```bash
./gradlew clean build

./gradlew bootRun
```

</br>

## 1. 프로젝트 개요

### ⚙️ 기술 스택
![java](https://img.shields.io/badge/Java-17-blue?logo=java)
![spring-boot](https://img.shields.io/badge/SpringBoot-3.3.3-6DB33F?logo=springboot)
![redis](https://img.shields.io/badge/Redis-7.4.0-FF4438)
![gRPC](https://img.shields.io/badge/gRPC-1.66.0-254c5b)

### ✔️ 요구사항

- 유저는 username, password를 가집니다.
- 유저가 RESTful API를 통해 인증서버에게 토큰 요청 가능해야 합니다 (ID/PW를 사용한 인증 방식)
- username과 password에는 정해진 format이 있어야 합니다.
- refreshToken과 accessToken을 발급합니다.

</br>

## 2. 프로젝트 관리

### 🗓️ 일정(2024.08.04~2024.08.11)

| 날짜 | 활동 |
| --- | --- |
| 24.08.04 (수) | 스켈레톤 프로젝트 생성, 지라, 이슈, PR 템플릿 설정 |
| 24.08.05 (목) | 엔티티 설계 |
| 24.08.06 (금) | 엔티티 생성, 스프링 시큐리티 기본 적용 |
| 24.08.07 (토) | 스프링 시큐리티+gRPC 결합 보안 설정, 사용자 API 구현 |
| 24.08.08 (일) | gRPC 통신 이용 JWT 파싱 구현 |
| 24.09.11 (수) | README.md 작성 및 Swagger 적용 |

<details>
<summary><strong>작업 사이클</strong></summary>

1. 이슈 생성
2. 브랜치 생성
3. 코드 작성
4. PR 생성
5. dev 브랜치로 Merge
</details>

<details>
<summary><strong>이슈 관리</strong></summary>
<img src=https://github.com/user-attachments/assets/a5341be5-1de7-433c-9e00-e6621f169dcf>
</details>

<details>
<summary><strong>컨벤션</strong></summary>

- **Branch**
    - **전략**

      | Branch Type | Description |
      | --- | --- |
      | `dev` | 주요 개발 branch, `main`으로 merge 전 거치는 branch |
      | `feature` | 할 일 issue 등록 후 branch 생성 및 작업 |

    - **네이밍**
        - `{header}/#{issue number}`
        - 예) `feat/#1`

- **커밋 메시지 규칙**
    ```bash
    > [HEADER] : 기능 요약
    
    - [CHORE]: 내부 파일 수정
    - [FEAT] : 새로운 기능 구현
    - [ADD] : FEAT 이외의 부수적인 코드 추가, 라이브러리 추가, 새로운 파일 생성 시
    - [FIX] : 코드 수정, 버그, 오류 해결
    - [DEL] : 쓸모없는 코드 삭제
    - [DOCS] : README나 WIKI 등의 문서 개정
    - [MOVE] : 프로젝트 내 파일이나 코드의 이동
    - [RENAME] : 파일 이름의 변경
    - [MERGE]: 다른 브렌치를 merge하는 경우
    - [STYLE] : 코드가 아닌 스타일 변경을 하는 경우
    - [INIT] : Initial commit을 하는 경우
    - [REFACTOR] : 로직은 변경 없는 클린 코드를 위한 코드 수정
    
    ex) [FEAT] 게시글 목록 조회 API 구현
    ex) [FIX] 내가 작성하지 않은 리뷰 볼 수 있는 버그 해결
    ```

- **Issue**
    ```bash
    ⭐️ Description
    <!-- 진행할 작업을 설명해주세요 -->
    
    ⭐️ To-do
    <!-- 작업을 수행하기 위해 해야할 태스크를 작성해주세요 -->
    [ ] todo1
    
    ⭐️ ETC
    <!-- 특이사항 및 예정 개발 일정을 작성해주세요 -->
    ```

- **PR**
  - **규칙**
    - branch 작업 완료 후 PR 보내기
    - 항상 local에서 충돌 해결 후 remote에 올리기
    - 충돌 확인 후 문제 없으면 merge
    -  merge
    ```bash
        > [MERGE] {브랜치이름}/{#이슈번호}
        ex) [MERGE] setting/#1
    ```
  - **Template**
    ```bash
    ⭐️ Description
    <!-- 진행할 작업을 설명해주세요 -->
    
    ⭐️ To-do
    <!-- 작업을 수행하기 위해 해야할 태스크를 작성해주세요 -->
    [ ] todo1
    
    ⭐️ ETC
    <!-- 특이사항 및 예정 개발 일정을 작성해주세요 -->
    ```
</details>

</br>

## 3. 기술 문서

### 📄 API 명세서

[Swagger API 명세서](http://localhost:8888/swagger-ui/index.html#/) </br>
[Postman API 명세서](https://documenter.getpostman.com/view/37810011/2sAXqmB5i8)

| API 명칭 | HTTP 메서드 | 엔드포인트 | 설명 |
| --- | --- | --- | --- |
| **사용자 회원가입** | POST | `/api/members` | 새로운 사용자를 등록합니다. |
| **사용자 로그인** | GET | `/api/members/login` | 사용자를 로그인시킵니다. |
| **사용자 로그아웃** | GET | `/api/members/logout`  | 사용자를 로그아웃시킵니다. |
| **refreshToken 재발급** | POST | `/api/members/refresh-token` | accessToken, refreshToken을 재발급 합니다. |

<details>
<summary><strong>ERD</strong></summary>
<img src=https://github.com/user-attachments/assets/40885a58-7c7b-4dee-a54e-5226e96301f7>
</details>

<details>
<summary><strong>디렉토리 구조</strong></summary>

```bash
├─main
│  ├─java
│  │  └─com
│  │      └─yonyk
│  │          └─talaria
│  │              └─auth
│  │                  │  TalariaAuthApplication.java
│  │                  │
│  │                  ├─common
│  │                  │  ├─anotation
│  │                  │  │  │  ValidMemberName.java
│  │                  │  │  │  ValidPassword.java
│  │                  │  │  │
│  │                  │  │  └─validator
│  │                  │  │          MemberNameValidator.java
│  │                  │  │          PasswordValidator.java
│  │                  │  │
│  │                  │  ├─config
│  │                  │  │      GrpcSecurityConfig.java
│  │                  │  │      SpringSecurityConfig.java
│  │                  │  │      SwaggerConfig.java
│  │                  │  │
│  │                  │  ├─security
│  │                  │  │  ├─details
│  │                  │  │  │      PrincipalDetails.java
│  │                  │  │  │      PrincipalDetailsService.java
│  │                  │  │  │
│  │                  │  │  ├─filter
│  │                  │  │  │      AuthenticationFilter.java
│  │                  │  │  │
│  │                  │  │  ├─grpc
│  │                  │  │  │      AuthenticationService.java
│  │                  │  │  │      CustomGrpcAuthenticationReader.java
│  │                  │  │  │      CustomServerInterceptor.java
│  │                  │  │  │
│  │                  │  │  ├─handler
│  │                  │  │  │      CustomAccessDeniedHandler.java
│  │                  │  │  │      CustomLogoutHandler.java
│  │                  │  │  │      CustomLogoutSuccessHandler.java
│  │                  │  │  │      SecurityExceptionHandler.java
│  │                  │  │  │
│  │                  │  │  ├─record
│  │                  │  │  │      JwtRecord.java
│  │                  │  │  │
│  │                  │  │  ├─redis
│  │                  │  │  │      RefreshToken.java
│  │                  │  │  │      RefreshTokenRepository.java
│  │                  │  │  │
│  │                  │  │  └─util
│  │                  │  │          CookieProvider.java
│  │                  │  │          JwtProvider.java
│  │                  │  │
│  │                  │  └─swagger
│  │                  │          MemberControllerSwagger.java
│  │                  │
│  │                  ├─controller
│  │                  │  │  MemberController.java
│  │                  │  │
│  │                  │  ├─request
│  │                  │  │      LoginDTO.java
│  │                  │  │      RegisterDTO.java
│  │                  │  │
│  │                  │  └─response
│  │                  │          dummy.txt
│  │                  │
│  │                  ├─entity
│  │                  │  │  BaseEntity.java
│  │                  │  │  Member.java
│  │                  │  │
│  │                  │  └─enums
│  │                  │          MemberRole.java
│  │                  │
│  │                  ├─exception
│  │                  │  │  CustomException.java
│  │                  │  │  CustomExceptionHandler.java
│  │                  │  │
│  │                  │  └─exceptionType
│  │                  │          CommonExceptionType.java
│  │                  │          ExceptionType.java
│  │                  │          RegisterExceptionType.java
│  │                  │          SecurityExceptionType.java
│  │                  │
│  │                  ├─repository
│  │                  │      MemberRepository.java
│  │                  │
│  │                  └─service
│  │                          JwtService.java
│  │                          MemberService.java
│  │
│  ├─proto
│  │      auth.proto
│  │
│  └─resources
│          application.properties
│          application.yml
│
└─test
    └─java
        └─com
            └─yonyk
                └─talaria
                    └─auth
                            TalariaAuthApplicationTests.java
```

</details>

</br>

## 4. 기능 구현

### ⭐ 사용자 인증 시스템

#### ✨ 회원가입

- 계정명, 이메일, 패스워드 제약조건 설정
- 각 제약조건은 커스텀 어노테이션 구현으로 검증
- 계정명, 이메일 중복 체크
- 비밀번호 암호화

<details>
    <summary>구현 의도</summary>
    <div>
        <div><strong>계정명, 이메일, 패스워드 제약조건</strong></div>
        <div>코드 재상용성이 높고 간편하게 유효성 검증을 할 수 이는 @Valid 어노테이션을 활용하여 구현하였습니다. 계정명, 패스워드는 기존 어노테이션으로는 충분한 검증이 불가하다고 생각해 커스텀 어노테이션을 생성하여 적용하였습니다.</div>
    </div>
</details>
<details>
    <summary>구현 코드</summary>
    <div>
        <a href="https://github.com/yony-k/TALARIA-AUTH/tree/dev/src/main/java/com/yonyk/talaria/auth/common/anotation" target="_blank">어노테이션 패키지</a></br>
        <a href="https://github.com/yony-k/TALARIA-AUTH/blob/dev/src/main/java/com/yonyk/talaria/auth/controller/MemberController.java" target="_blank">회원가입: signUp 메소드</a></br>
    </div>
</details>

#### ✨ 로그인

- 스프링 시큐리티 적용
- 커스텀 필터를 활용한 로그인 인증 구현
- 스프링 시큐리티의 핸들러를 커스텀하여 전역 예외 처리
- 대칭키 방식을 이용해 JWT 생성 및 발급
- accessToken은 헤더, refreshToken은 쿠키에 넣어서 반환
- refreshToken은 서버의 Redis에 저장

<details>
    <summary>구현 의도</summary>
    <div>
        <div><strong>스프링 시큐리티</strong></div>
        <div>금을 판매하는 사이트인만큼 보안이 중요하다고 생각해서 스프링 시큐리티를 구현하였습니다. 비록 인증서버에서는 특별한 권한이 필요한 API는 없지만 인증서버가 확장될 수도 있고 사용자의 정보를 보관하는 서버이니 최대한 보안을 챙길수록 좋다고 생각합니다.</div>
        <div><strong>커스텀 필터</strong></div>
        <div>JWT를 발급해야하기 때문에 기존 form로그인 방식으로는 불가하다고 판단하여 커스텀 필터를 구현하여 로그인을 처리했습니다. 로그인 성공시/실패시 각각 다른 응답을 보낼 수 있기 때문에 더 유용하다고 생각했습니다.</div>
        <div><strong>대칭키 방식 JWT 생성</strong></div>
        <div>처음에는 인증서버, 자원서버가 나뉘어져있기 때문에 비대칭 방식을 이용하려고 했습니다. 하지만 gRPC 통신을 통한 인증은 인증서버에서 자원서버에서 보내 온 accessToken을 파싱하여 사용자 정보를 보내주는 형식으로 결국 한 서버 내에서 생성과 파싱이 이루어지기 때문에 굳이 비대칭 방식을 택할 필요가 없다고 생각해 대칭키 방식으로 구현했습니다.</div>
        <div><strong>refreshToken 발급</strong></div>
        <div>보안적으로 accessToken의 만료시간을 짧게 하고 refreshToken으로 재발급을 하는 편이 좋다고 생각해 refreshToken 발급하는 방식을 채택했습니다.</div>
        <div><strong>refreshToken을 Redis에 저장</strong></div>
        <div>aacessToken의 만료시간이 짧기 때문에 refreshToken을 이용한 재발급 요청이 그만큼 자주 발생하는데 그때마다 DB에 접근하는 것은 부담이 될 것 같아 상대적으로 접근이 쉽고 처리속도가 빠른 Redis에 저장하게 되었습니다.</div>
    </div>
</details>
<details>
    <summary>구현 코드</summary>
    <div>
        <a href="https://github.com/yony-k/TALARIA-AUTH/tree/dev/src/main/java/com/yonyk/talaria/auth/common/security" target="_blank">Spring Security 패키지</a></br>
    </div>
</details>

#### ✨ 로그아웃
- 커스텀한 LogoutHandler를 사용
- 쿠키에 저장된 refreshToken 만료, Redis에 저장되어있는 refreshToken 삭제로 로그아웃 구현
- 커스텀한 LogoutSuccessHandler 를 통해 사용자에게 응답 반환

<details>
    <summary>구현 의도</summary>
    <div>
        <div><strong>커스텀 필터</strong></div>
        <div>JWT 방식으로 로그인을 하기 때문에 서버와 클라이언트 양쪽에 사용자의 정보(refreshToken)가 남게되었습니다. 로그아웃 시 이 정보를 모두 지워줘야 맞다고 생각했고 이런 일을 처리하기 위해 존재하는 LogoutHandler를 커스텀하여 사용하게 됐습니다.</div>
    </div>
</details>
<details>
    <summary>구현 코드</summary>
    <div>
        <a href="https://github.com/yony-k/TALARIA-AUTH/blob/dev/src/main/java/com/yonyk/talaria/auth/common/security/handler/CustomLogoutHandler.java" target="_blank">CustomLogoutHandler</a></br>
        <a href="https://github.com/yony-k/TALARIA-AUTH/blob/dev/src/main/java/com/yonyk/talaria/auth/common/security/handler/CustomLogoutSuccessHandler.java" target="_blank">CustomLogoutSuccessHandler</a></br>
    </div>
</details>

---

### ⭐ gRPC 통신을 이용한 JWT 인가 시스템

- gRPC 서버 구현
- 클라이언트에서 보내온 accessToken을 파싱하여 사용자 정보 반환
- 스프링 시큐리티와 결합하여 보안 향상
- 인터셉터 구현하여 인증 및 예외 처리

<details>
    <summary>구현 의도</summary>
    <div>
        <div><strong>인터셉터 구현</strong></div>
        <div>스프링 시큐리티를 구현하니 gRPC 통신 또한 인증이 필수적이게 되었습니다. 이를 위해 필요한 클래스가 GrpcAuthenticationReader 였는데 여기서 발생하는 예외를 안전하게 처리하기 위해서 인터셉터를 구현했습니다.</div>
    </div>
</details>
<details>
    <summary>구현 코드</summary>
    <div>
        <a href="https://github.com/yony-k/TALARIA-AUTH/tree/dev/src/main/java/com/yonyk/talaria/auth/common/security/grpc" target="_blank">gRPC 패키지</a></br>
    </div>
</details>

</br>

## 5. 트러블 슈팅

<details>
    <summary>스프링 시큐리티와 gRPC 결합</summary>
    <div>
        <div><strong>문제상황</strong></div>
        <div>스프링 시큐리티 없이 기본 gRPC를 구현했을 때는 문제없이 돌아가던 서버가 스프링 시큐리티를 적용하자마자 오류가 발생했다. </div>
        <div><strong>원인</strong></div>
        <div>알고보니 스프링 시큐리티는 HTTP 기반 요청처리에 적합한 프레임워크고 gRPC는 HTTP/2 기반이기 때문에 스프링 시큐리티에서 gRPC를 사용하려면 추가적인 인증 로직이 필요했던 것이다.</div>
        <div><strong>해결</strong></div>
        <div>GrpcAuthenticationReader 라는 클래스가 이런 인증 로직을 처리하는 전용 클래스로 이 클래스를 구현하여 해결했다. 이 클래스는 gRPC 서버 호출 시 클라이언트로부터 전달된 메타데이터에서 정보를 읽어들여 스프링 시큐리티 컨택스트에 올릴 수 있는 인증 객체를 생성하는 역할을 한다.</div>
    </div>
</details>

<details>
    <summary>gRPC 예외처리</summary>
    <div>
        <div><strong>문제상황</strong></div>
        <div>GrpcAuthenticationReader 를 이용해서 인증객체를 생성할 때 JWT 파싱 과정을 거치는데 이때 꽤 많은 예외가 발생한다. 예외가 발생하면 클라이언트에게 인증 실패 메세지를 전송해야하는데 GrpcAuthenticationReader 내에서 처리하기에는 부적절해보였다.</div>
        <div><strong>해결</strong></div>
        <div>ServerInterceptor 라는 클래스는 gRPC로 서버로 오는 요청이나 서버에서 나가는 응답을 가로채어 적절한 처리를 하는 클래스인데 이 클래스에서는 클라이언트에 응답을 보내는 것도 간단하게 처리할 수 있다. 이걸 구현하여 이 안에서 GrpcAuthenticationReader 가 호출되게 하고 이때 예외가 발생하면 적절한 메세지가 클라이언트로 반환되도록 하였다.</div>
    </div>
</details>
