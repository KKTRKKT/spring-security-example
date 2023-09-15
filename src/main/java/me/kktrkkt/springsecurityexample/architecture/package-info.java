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
                              인증시 UserDetailsService에서 UserDetails 정보를 가져와서 사용자가 입력한 정보(username, password)와 비교해 인증하게 된다.
                              인증이 유효하면 Autehncation의 Principal에 유저 정보를 담아서 반환하고 인증이 유효하지 않으면 비활성화 계정, 잘못된 비번, 잠긴 계정등의 예외를 던진다

UsernamePasswordAuthenticationFilter: 아이디와 비밀번호로 로그인할 때 동작하는 인증 필터다.
                                      아이디와 비밀번호를 담고 있는 UsernamePasswordAutehncation이라는 Authentication을 생성
                                      AuthenticationManager를 통해 인증 수행
                                      인증 성공시 유저 정보를 담은 Authentication을 반환한다.

SecurityContextPersistenceFilter: 인증된 사용자 정보를 유지하고 다음 요청에서 사용할 수 있도록 도와주는 필터다
                                  요청이 들어오면  HTTP 세션, 쿠키, 또는 다른 저장 메커니즘을 통해 이전 요청에서 저장한 SecurityContext를 가져와 SecurityContextHolder에 넣는다
                                  요청이 처리되는 동안에는 전역적으로 SecurityContext를 사용할 수 있게된다
                                  요청이 끝난 뒤에는 저장매커니즘을 이용해 SecurityContext를 저장한다

 */