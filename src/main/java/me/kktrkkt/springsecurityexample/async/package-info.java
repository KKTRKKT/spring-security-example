package me.kktrkkt.springsecurityexample.async.service_filter;

/*
@Async 태그를 통해 비동기를 사용하는 경우에 SecurityContextHolder에 접근하려면 아래와 같이 Strategy 설정을 해줘야 한다
SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);

 */