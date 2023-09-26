package me.kktrkkt.springsecurityexample.async.service_filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private HelloService helloService;

    @GetMapping
    public String hello() throws InterruptedException {
        SecurityUtils.log("before service");
        helloService.doService();
        SecurityUtils.log("after service");
        return "hello";
    }

}
