package me.kktrkkt.springsecurityexample.async;

/*
핸들러의 리턴값을 비동기 객체를 주게 되면 WebAsyncManagerIntegrationFilter에 의해서 해당 비동기 객체 처리 로직안에서도 SecurityContextHolder를 사용할 수 있다
순서
1. 비동기 객체가 SecurityContextHolder를 사용할 수 있도록 설정한다
2. 비동기 객체는 다른 스레드를 통해 실행되지만 SecurityContextHolder를 참조할 수 있다
3. SecurityContextHolder를 정리한다

 */