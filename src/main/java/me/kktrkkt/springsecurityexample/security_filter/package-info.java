package me.kktrkkt.springsecurityexample.security_filter;

/*
DisableEncodeUrlFilter: 세션 ID가 URL에 노출되는것을 방지한다.
WebAsyncManagerIntegrationFilter: 핸들러에서 Callable을 사용할 시 Callable 내에서 SecurityContext를 사용할 수 있도록 설정해준다.
SecurityContextPersistenceFilter: SecurityContext를 영속화 하는 필터로 저장 방식(Session, requestAttribute, redis) 에 따라 SC를 저장하고, 꺼낸다.
HeaderWriterFilter: 시큐리티 관련 헤더를 추가해준다. 마임 타입 스니핑 방어, 브라우저에 저장된 XSS 필터 적용, 캐시 히스토리 취약점 방어, HTTPS 소통하도록 강제, ClickJacking 방어
    - 마임 타입 스니핑 방어: X-Content-Type-Options: nosniff
    - 브라우저에 내장된 XSS 필터 적용: X-XSS-Protection: 1; mode=block
    - 캐시 히스토리 취약점 방어: [Cache-Control: no-cache, no-store, max-age=0, must-revalidate], [Expires: 0], [Pragma: no-cache]
    - HTTPS로만 소통하도록 강제: Strict-Transport-Security: max-age=31536000; includeSubDomains
    - clickjacking 방어: X-XSS-Protection: 1; mode=block
CsrfFilter: CSRF(사이트간 요청 위조) 공격을 방지한다. csrf 토큰을 통해 방지한다, http().csrf().disable()로 비활성화 가능
LogoutFilter: 로그아웃을 담당하는 필터로 로그아웃 url, 로그아웃 처리, 로그아웃 성공 후 처리 및 url 설정 등을 담당한다. (http.logout()을 통해 설정)
UsernamePasswordAuthenticationFilter: 아이디와 비밀번호로 로그인할 때 동작하는 인증 필터다. 자세한건 architecture.pakcage-info 참고
DefaultLoginGeneratingFilter/DefaultLogoutGeneratingFilter: 로그인/로그아웃 페이지를 생성해주는 필터 (http.formLogin().usernameParameter().passwordParameter()로 파라미터명 설정 가능)
BasicAuthenticationFilter: http 요청에 base64로 인코딩한 계정정보(id:password)를 넣어주면 인증되는 필터
RequestCacheAwareFilter: 인증되지 않은 요청을 저장하고, 인증이 되면 해당 요청을 처리하는 필터 (AccessDecisionManager 처리 전에 요청을 저장한다)
SecurityContextHolderAwareRequestFilter: HttpServletRequest API를 구현하는 필터로 authenticate, login, logout, start를 구현한다
AnonymousAuthenctaionFilter: SecurityContext에 Authentication이 없으면 익명 Authentication을 넣어준다 (http.anonymous() 를 통해 커스텀 가능하다)
SessionManagementFilter: 세션 변조 방지, 세션 만료관련 설정, 최대 세션 설정, 세션 생성 전략등 세션과 관련된 기능을 제공해주는 필터다
ExceptionTranslationFilter: AuthenticationException 및 AuthorizationException을 처리하는 필터, 인증 및 인가 실패 화면 설정 또는 핸들러를 설정할 수 있다 (http.exceptionHandling())
FilterSecurityInterceptor: AccessDecisionManager를 통해 uri의 인가를 처리하는 필터, architectrue/access_decision_manager 참고
 */