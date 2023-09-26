package me.kktrkkt.springsecurityexample.security_filter.web_async_manager_integration_filter;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.Callable;

@Controller
@RequestMapping("/async")
public class AsyncController {

    @GetMapping
    @ResponseBody
    public Callable<String> getAsync() {
        log("getAsync");
        return () -> {
            log("Callable");
            return "hello world";
        };
    }

    private void log(String name) {
        System.out.println(name);
        System.out.println("Thread: " + Thread.currentThread());
        System.out.println("Principal: " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

}
