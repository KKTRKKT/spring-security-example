package me.kktrkkt.springsecurityexample.security_filter.exception_translation_filter;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
403 에러가 발생할 시, 시도자의 정보를 로깅한다
 */

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String name = request.getUserPrincipal().getName();
        System.out.println(name + " is denied to access " + request.getRequestURI());
        request.getRequestDispatcher("/access-denied").forward(request, response);
    }
}
