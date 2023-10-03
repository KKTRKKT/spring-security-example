package me.kktrkkt.springsecurityexample.security_filter.base_authentication_filter;

/*
http.httpBasic() 설정 예제

basic으로 인증을 할시 stateless하며, 요청을 탈취당하면 인증 정보를 탈취당할 수 있으므로 https 사용이 필수다

curl -u user:password localhost:8081

 */