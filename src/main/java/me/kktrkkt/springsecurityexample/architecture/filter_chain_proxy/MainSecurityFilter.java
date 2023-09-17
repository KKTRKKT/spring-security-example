package me.kktrkkt.springsecurityexample.architecture.filter_chain_proxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class MainSecurityFilter {

    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE - 20)
    public SecurityFilterChain mainSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .formLogin();
        return http.build();
    }
}
