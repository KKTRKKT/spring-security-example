package me.kktrkkt.springsecurityexample.architecture;

/*

SecurityContextHolder: SecurityContext를 담고 있는 객체이며, 기본적으로 ThreadLocal을 사용한다
    - SecurityContext: Authentication를 담고 있는 객체
        - Authentication: Principal, GrantAuthority 정보를 제공하는 객체
            - Principal: 유저정보를 담는 객체로 UserDetails 타입의 데이터가 담겨져 있다. UserDetails는 UserDetailsService를 통해 가져온다.
                - UserDetails: username, password, authorities 등의 대한 정보가 담긴다
                - UserDetailsService: DB에서 가져온 유저정보를 UserDetails로 변환해서 반환하는 역할을 한다
            - GrantAuthority: 권한 정보를 담는 객체로 GrantedAuthority 타입의 데이터들의 목록이 담겨져 있다
    - ThreadLocal: 한 Thread 내에서 데이터를 공유할 수 있는 일종의 저장소

AuthenticationManager: 하나 이상의 AuthenticationProvider를 통해 Authentication이 유효한지 확인하는 인터페이스로 ProviderManager가 구현한다.
                       처리할 수 없으면 부모 ProviderManager를 호출해 위 과정을 반복한다
    - AuthenticationProvider: 인증할 수 있는 Authencation 확인 로직관 실제 인증이 처리되는 로직이 담겨있다.
                              AuthenticationProvider 중 DaoAuthenticationProvider에 의해 UserDetailsService에서 UserDetails 정보를 가져와서 사용자가 입력한 정보(username, password)와 비교해 인증하게 된다.
                              인증이 유효하면 Autehncation의 Principal에 유저 정보를 담아서 반환하고 인증이 유효하지 않으면 비활성화 계정, 잘못된 비번, 잠긴 계정등의 예외를 던진다

UsernamePasswordAuthenticationFilter: 아이디와 비밀번호로 로그인할 때 동작하는 인증 필터다.
                                      아이디와 비밀번호를 담고 있는 UsernamePasswordAutehncation이라는 Authentication을 생성
                                      AuthenticationManager를 통해 인증 수행
                                      인증 성공시 유저 정보를 담은 Authentication을 반환한다.

SecurityContextPersistenceFilter: 인증된 사용자 정보를 유지하고 다음 요청에서 사용할 수 있도록 도와주는 필터다
                                  요청이 들어오면  HTTP 세션, 쿠키, 또는 다른 저장 메커니즘을 통해 이전 요청에서 저장한 SecurityContext를 가져와 SecurityContextHolder에 넣는다
                                  요청이 처리되는 동안에는 전역적으로 SecurityContext를 사용할 수 있게된다
                                  요청이 끝난 뒤에는 저장매커니즘을 이용해 SecurityContext를 저장한다

FilterChainProxy: FilterChain을 관리하는 주체로, 모든 요청은 FilterChainProxy로 들어와 URI 패턴 등에 따라 적절한 FilterChain을 적용해 요청을 처리한다
    - FilterChain: SecurityFilter를 체인 형태로 구성한것으로 요청이 이 체인의 흐름에 따라 보안관련 작업을 수행한다
        - SecurityFilter: 보안 관련 작업을 처리하는 필터로 인증, 권한부여, 세션관리 등의 작업을 처리한다(ex UsernamePasswordAuthenticationFilter, SecurityContextPersistenceFilter)

DelegatingFilterProxy: 서블릿 컨테이너에 등록된 필터로 요청이 들어오면 SpringApplicationContext에 등록된 FilterChainProxy에게 위임한다.

AccessDecisionManager: 인가를 결정하는 인터페이스다. AccessDecisionVoter들의 찬성 및 반대 결과를 취합해 인가 여부를 결정한다
                       구현체에는 AffrmativeBased(찬성이 하나라도 나오면 인가), ConsensusBased(다수결이면 인가), UnanimousBased(만장일치면 인가)가 있다.
    - AccessDecisionVoter: 인증 후 생긴 Authentication(인증 정보)과 ConfigAttribute(권한 정보)를 통해 인가 여부를 결정한다
        - ConfigAttribute: 권한을 나타내는 인터페이스로 문자열로 표현된다 (.mvcMatchers("/user").hasRole("USER")에서 "USER" 같은 문자열)

FilterSecurityInterceptor: SecurityFilter 중 하나로 AccessDecisionManager를 통해 인가를 결정한다. 인가 실패시 예외를 던진다
                           doFilter -> invoke -> super.beforeInvocation -> attemptAuthorization -> accessDecisionManager.decide

ExceptionTranslationFilter: FilterSecurityInterceptor에서 던지는 AuthenticationExcpetion과 AccessDeniedException을 처리하는 필터다.
                            FilterChain에서 ExceptionTranslationFilter보다 순위가 낮은 모든 Filter들의 Excpetion을 잡으나 기본적으로 FilterSecurityInterceptor만 상대적으로 순위가 낮다
                            AuthenticationExcpetion이 발생하면 AuthenticationEntryPoint를 실행해 인증받도록한다.
                            AccessDeniedException이 발생하면 사용자 권한에 따라 달라진다.
                            - 익명: AuthenticationException처럼 재인증 받게 한다.
                            - 인증 유저: AccessDeniedHandler를 통해 처리하는데, 기본적을로 에러페이지가 존재하면 에러페이지로 이동하고 없으면 본문에 403 에러 메시지를 담아 반환한다

 */