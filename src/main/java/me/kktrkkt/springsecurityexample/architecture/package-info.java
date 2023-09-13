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




 */