package me.kktrkkt.springsecurityexample.security_filter.remember_me_authentication_filter;

/*
RememberMeAuthenticationFilter는 로그인 상태를 유지할 수 있도록 지원해주는 필터다.

실행 순서
1. 클라이언트가 remember me 체크박스를 체크 후 로그인
2. remember-me 토큰을 발행해 클라이언트 쿠키에 저장
3. SecurityContextHolderAwareRequestFilter에서 실행 후 Authentication null일 경우
RememberMeAuthenticationFilter에서 SecurityContext에 기억한 Authencation을 넣어준다

 */