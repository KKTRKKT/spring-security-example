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

 */