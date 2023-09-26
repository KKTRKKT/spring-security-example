package me.kktrkkt.springsecurityexample.security_filter;

/*
DisableEncodeUrlFilter: 세션 ID가 URL에 노출되는것을 방지한다.
WebAsyncManagerIntegrationFilter: 핸들러에서 Callable을 사용할 시 Callable 내에서 SecurityContext를 사용할 수 있도록 설정해준다.
