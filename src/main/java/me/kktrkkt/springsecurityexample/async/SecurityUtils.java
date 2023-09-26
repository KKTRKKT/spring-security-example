package me.kktrkkt.springsecurityexample.async;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static void log(String name) {
        System.out.println(name);
        System.out.println("Thread: " + Thread.currentThread());
        System.out.println("Principal: " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
