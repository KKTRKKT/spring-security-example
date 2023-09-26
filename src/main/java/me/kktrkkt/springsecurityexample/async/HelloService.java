package me.kktrkkt.springsecurityexample.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    @Async
    public void doService() throws InterruptedException {
        SecurityUtils.log("doService");
        Thread.sleep( 5000L);
    }
}
