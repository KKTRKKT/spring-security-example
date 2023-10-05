package me.kktrkkt.springsecurityexample.security_filter.session_management_filter;

/*
SessionManagementFilter는 사용자의 세션 보안및 관리해주는 필터다

- *세션 변조 방지 : 로그인시마다 세션을 생성 또는 변경하는 전략을 선택할 수 있다.
- 유효하지 않은 세션 처리 : 세션 만료시 이동 페이지를 설정할 수 있다.
- 동시성 제어: 사용자당 최대 세션 수를 설정할 수 있다
- 세션 생성 전략: IF_REQUIRED, NEVER, STATELESS, ALWAYS

 * 서버는 클라이언트의 세션ID가 같으면 같은 사용자로 보기 때문에, 사용자와 공격자가 같은 세션 ID를 통해 요청하는것을 방지해야한다

 */