package me.kktrkkt.springsecurityexample.async.service_filter;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;

@Service
public class HelloService {

    @Async
    public void doService() throws InterruptedException {
        SecurityUtils.log("doService");
        Thread.sleep( 5000L);
    }
}
