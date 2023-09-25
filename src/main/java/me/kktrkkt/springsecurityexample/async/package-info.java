package me.kktrkkt.springsecurityexample.async;

/*
핸들러의 리턴값을 비동기 객체를 주게 되면 WebAsyncManagerIntegrationFilter에 의해서 해당 비동기 객체 처리 로직안에서도 SecurityContextHolder를 사용할 수 있다

 */