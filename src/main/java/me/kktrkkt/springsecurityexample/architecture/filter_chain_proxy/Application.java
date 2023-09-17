package me.kktrkkt.springsecurityexample.architecture.filter_chain_proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 필터체인은 여러개를 만들수 있고, 구체적인 필터체인이 먼저 등록되어야 적용될 수 있다
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
