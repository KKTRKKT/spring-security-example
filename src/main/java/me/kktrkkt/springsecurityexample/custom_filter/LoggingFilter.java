package me.kktrkkt.springsecurityexample.custom_filter;

import org.springframework.util.StopWatch;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LoggingFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        StopWatch stopWatch = new StopWatch();
        stopWatch.start(req.getRequestURI());

        filterChain.doFilter(servletRequest, servletResponse);

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }
}
