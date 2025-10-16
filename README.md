# Spring Security Example

Spring Security의 다양한 기능과 아키텍처를 학습하기 위한 예제 프로젝트입니다.

## 📚 목차

- [프로젝트 개요](#프로젝트-개요)
- [기술 스택](#기술-스택)
- [예제 구조](#예제-구조)
  - [1. Simple - 기본 설정 및 테스트](#1-simple---기본-설정-및-테스트)
  - [2. Architecture - 스프링 시큐리티 아키텍처](#2-architecture---스프링-시큐리티-아키텍처)
  - [3. Security Filter - 보안 필터](#3-security-filter---보안-필터)
  - [4. Custom Filter - 커스텀 필터](#4-custom-filter---커스텀-필터)
  - [5. Async - 비동기 처리](#5-async---비동기-처리)
- [실행 방법](#실행-방법)

---

## 프로젝트 개요

이 프로젝트는 Spring Security의 핵심 개념과 다양한 필터들의 동작 원리를 이해하기 위한 실습 예제들을 포함하고 있습니다. 
각 패키지별로 독립적인 Spring Boot 애플리케이션이 구성되어 있으며, 테스트 코드를 통해 동작을 검증할 수 있습니다.

## 기술 스택

- **Spring Boot**: 2.7.16-SNAPSHOT
- **Spring Security**: 5.7.x
- **Java**: 11
- **Database**: H2 (In-memory)
- **ORM**: Spring Data JPA
- **Template Engine**: Thymeleaf
- **Build Tool**: Maven

---

## 예제 구조

### 1. Simple - 기본 설정 및 테스트

**경로**: `src/main/java/me/kktrkkt/springsecurityexample/simple`

Spring Security의 기본적인 설정과 테스트 코드 작성 방법을 다룹니다.

**주요 내용**:
- Spring Security 기본 설정
- Form 로그인 구현
- 사용자 인증 및 권한 관리
- MockMvc를 이용한 시큐리티 테스트

**테스트 코드**:
- `FormLoginTest.java`: Form 로그인 성공/실패 테스트
- `SimpleControllerTest.java`: 컨트롤러 권한 테스트
- `WithUser.java`, `WithAdmin.java`: 커스텀 테스트 어노테이션

---

### 2. Architecture - 스프링 시큐리티 아키텍처

**경로**: `src/main/java/me/kktrkkt/springsecurityexample/architecture`

Spring Security의 핵심 아키텍처 구성 요소들을 학습합니다.

#### 2.1 주요 개념

**SecurityContextHolder**
- SecurityContext를 담고 있는 객체
- 기본적으로 ThreadLocal을 사용
- SecurityContext는 Authentication 정보를 담고 있음

**Authentication**
- Principal과 GrantedAuthority 정보를 제공
- Principal: UserDetails 타입의 사용자 정보
- GrantedAuthority: 권한 정보 목록

**AuthenticationManager**
- 하나 이상의 AuthenticationProvider를 통해 인증 처리
- ProviderManager가 구현체
- 처리할 수 없으면 부모 ProviderManager를 호출

**AuthenticationProvider**
- 실제 인증 로직이 처리되는 곳
- DaoAuthenticationProvider: UserDetailsService를 통해 사용자 정보를 가져와 비교
- 인증 성공 시 Authentication 반환, 실패 시 예외 발생

#### 2.2 필터 체인

**FilterChainProxy** (`architecture/filter_chain_proxy`)
- FilterChain을 관리하는 주체
- URI 패턴에 따라 적절한 FilterChain을 적용
- 여러 개의 필터 체인을 구성 가능

**특징**:
- 구체적인 필터 체인이 먼저 등록되어야 함
- 모든 요청은 FilterChainProxy를 거쳐 처리

#### 2.3 인가 처리

**AccessDecisionManager** (`architecture/access_decision_manager`)
- 인가를 결정하는 인터페이스
- AccessDecisionVoter들의 결과를 취합하여 최종 인가 여부 결정

**구현체**:
- `AffirmativeBased`: 찬성이 하나라도 있으면 인가
- `ConsensusBased`: 다수결로 인가
- `UnanimousBased`: 만장일치로 인가

**AccessDecisionVoter**:
- Authentication(인증 정보)과 ConfigAttribute(권한 정보)를 통해 인가 여부 결정
- ConfigAttribute: 권한을 나타내는 문자열 (예: "ROLE_USER")

**FilterSecurityInterceptor**:
- AccessDecisionManager를 통해 URI 인가 처리
- 인가 실패 시 예외 발생

**ExceptionTranslationFilter**:
- AuthenticationException과 AccessDeniedException 처리
- FilterSecurityInterceptor보다 앞에 위치
- 익명 사용자: 재인증 유도
- 인증된 사용자: AccessDeniedHandler로 처리 (기본: 403 에러)

**테스트 코드**:
- `AccessDecisionManagerTest.java`: 인가 처리 테스트

---

### 3. Security Filter - 보안 필터

**경로**: `src/main/java/me/kktrkkt/springsecurityexample/security_filter`

Spring Security의 다양한 보안 필터들의 동작 방식을 학습합니다.

#### 3.1 필터 목록 및 설명

**DisableEncodeUrlFilter**
- 세션 ID가 URL에 노출되는 것을 방지

**WebAsyncManagerIntegrationFilter** (`web_async_manager_integration_filter`)
- 핸들러의 비동기 객체(Callable) 내에서 SecurityContext 사용 가능하게 설정
- 다른 스레드에서도 SecurityContextHolder 참조 가능

**SecurityContextPersistenceFilter**
- SecurityContext를 영속화하는 필터
- 요청 시: HTTP 세션, 쿠키 등에서 SecurityContext를 가져와 SecurityContextHolder에 저장
- 응답 시: SecurityContext를 저장 매커니즘을 통해 저장

**HeaderWriterFilter**
- 시큐리티 관련 헤더 추가
  - `X-Content-Type-Options: nosniff`: 마임 타입 스니핑 방어
  - `X-XSS-Protection: 1; mode=block`: XSS 필터 적용
  - `Cache-Control`: 캐시 히스토리 취약점 방어
  - `Strict-Transport-Security`: HTTPS 강제
  - `X-Frame-Options`: ClickJacking 방어

**CsrfFilter** (`csrf_filter`)
- CSRF(사이트간 요청 위조) 공격 방지
- CSRF 토큰을 통해 방어
- JSP, Thymeleaf 사용 시 `_csrf` input 자동 추가
- `http().csrf().disable()`로 비활성화 가능

**예제**: 회원가입 화면에서 CSRF 토큰 동작 확인

**LogoutFilter**
- 로그아웃 처리 담당
- 로그아웃 URL, 핸들러, 성공 후 처리 설정
- `http.logout()`을 통해 설정

**UsernamePasswordAuthenticationFilter**
- 아이디와 비밀번호로 로그인 처리
- UsernamePasswordAuthentication 생성 → AuthenticationManager로 인증
- 인증 성공 시 사용자 정보를 담은 Authentication 반환

**DefaultLoginGeneratingFilter / DefaultLogoutGeneratingFilter**
- 기본 로그인/로그아웃 페이지 생성
- `http.formLogin().usernameParameter()`, `passwordParameter()`로 파라미터명 설정 가능

**BasicAuthenticationFilter** (`base_authentication_filter`)
- HTTP Basic 인증 처리
- Base64로 인코딩된 계정 정보(`id:password`)로 인증
- Stateless하며, HTTPS 사용 필수
- 사용법: `curl -u user:password localhost:8081`

**RequestCacheAwareFilter**
- 인증되지 않은 요청을 저장
- 인증 완료 후 저장된 요청을 처리

**SecurityContextHolderAwareRequestFilter**
- HttpServletRequest API 구현
- `authenticate()`, `login()`, `logout()`, `startAsync()` 제공

**AnonymousAuthenticationFilter**
- SecurityContext에 Authentication이 없으면 익명 Authentication 추가
- `http.anonymous()`로 커스터마이징 가능

**SessionManagementFilter** (`session_management_filter`)
- 세션 보안 및 관리
  - **세션 변조 방지**: 로그인 시마다 세션 생성/변경 전략 선택
  - **유효하지 않은 세션 처리**: 세션 만료 시 이동 페이지 설정
  - **동시성 제어**: 사용자당 최대 세션 수 설정
  - **세션 생성 전략**: IF_REQUIRED, NEVER, STATELESS, ALWAYS

**ExceptionTranslationFilter** (`exception_translation_filter`)
- AuthenticationException과 AccessDeniedException 처리
- FilterChain에서 순위가 낮은 필터들의 예외를 처리
- `http.exceptionHandling()`을 통해 인가 실패 페이지 변경 및 로깅 설정

**FilterSecurityInterceptor**
- AccessDecisionManager를 통해 URI 인가 처리
- 순서: `doFilter` → `invoke` → `super.beforeInvocation` → `attemptAuthorization` → `accessDecisionManager.decide`

**RememberMeAuthenticationFilter** (`remember_me_authentication_filter`)
- Remember Me 기능 제공
- 토큰을 쿠키에 저장하여 로그인 상태 유지
- `http.rememberMe()`로 설정

**실행 순서**:
1. 클라이언트가 Remember Me 체크박스 체크 후 로그인
2. Remember-me 토큰 발행하여 쿠키에 저장
3. SecurityContextHolderAwareRequestFilter 실행 후 Authentication이 null인 경우
4. RememberMeAuthenticationFilter가 SecurityContext에 저장된 Authentication 추가

**테스트 코드**:
- `SignupControllerTest.java`: CSRF 필터 테스트 (회원가입)

---

### 4. Custom Filter - 커스텀 필터

**경로**: `src/main/java/me/kktrkkt/springsecurityexample/custom_filter`

SecurityFilterChain에 커스텀 필터를 추가하는 방법을 학습합니다.

**주요 내용**:
- 커스텀 필터 생성
- SecurityFilterChain에 필터 추가
- 필터 순서 지정

**예제**:
- `LoggingFilter.java`: 요청 정보를 로깅하는 커스텀 필터
- `SecurityConfig.java`: 커스텀 필터 등록 설정

---

### 5. Async - 비동기 처리

**경로**: `src/main/java/me/kktrkkt/springsecurityexample/async`

비동기 처리 시 SecurityContext 접근 방법을 학습합니다.

**주요 내용**:
- `@Async` 어노테이션 사용 시 SecurityContextHolder 설정
- Strategy 설정 필요:
  ```java
  SecurityContextHolder.setStrategyName(
      SecurityContextHolder.MODE_INHERITABLETHREADLOCAL
  );
  ```
- 다른 스레드에서 SecurityContext 접근 가능

---

## 실행 방법

각 예제는 독립적인 Application 클래스를 가지고 있습니다.

### 1. 특정 예제 실행

```bash
# Simple 예제 실행
./mvnw spring-boot:run -Dstart-class=me.kktrkkt.springsecurityexample.simple.Application

# CSRF 필터 예제 실행
./mvnw spring-boot:run -Dstart-class=me.kktrkkt.springsecurityexample.security_filter.csrf_filter.Application

# FilterChainProxy 예제 실행
./mvnw spring-boot:run -Dstart-class=me.kktrkkt.springsecurityexample.architecture.filter_chain_proxy.Application
```

### 2. 테스트 실행

```bash
# 전체 테스트 실행
./mvnw test

# 특정 테스트 실행
./mvnw test -Dtest=FormLoginTest
./mvnw test -Dtest=AccessDecisionManagerTest
```

### 3. 애플리케이션 접속

대부분의 예제는 기본 포트 `8080` 또는 `8081`에서 실행됩니다.

```
http://localhost:8080
http://localhost:8081
```

---

## 학습 순서 추천

1. **Simple**: Spring Security 기본 설정과 테스트 방법 이해
2. **Architecture/filter_chain_proxy**: 필터 체인의 동작 원리 이해
3. **Architecture/access_decision_manager**: 인가 처리 메커니즘 이해
4. **Security Filter**: 각 필터들의 역할과 동작 방식 학습
   - base_authentication_filter (기본 인증)
   - csrf_filter (CSRF 방어)
   - remember_me_authentication_filter (로그인 유지)
   - session_management_filter (세션 관리)
   - exception_translation_filter (예외 처리)
   - web_async_manager_integration_filter (비동기 처리)
5. **Custom Filter**: 커스텀 필터 추가 방법 학습
6. **Async**: 비동기 환경에서 SecurityContext 사용 방법 학습

---

## 참고사항

- 각 패키지의 `package-info.java` 파일에 상세한 설명이 포함되어 있습니다.
- 테스트 코드를 통해 각 기능의 동작을 확인할 수 있습니다.
- 실제 운영 환경에 적용하기 전에 각 보안 설정의 의미를 충분히 이해하고 사용하시기 바랍니다.
