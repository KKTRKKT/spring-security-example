package me.kktrkkt.springsecurityexample.architecture.filter_chain_proxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SubSecurityFilter {

    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE - 30)
    public SecurityFilterChain subSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .antMatcher("/sub/**")
            .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin();
        return http.build();
    }
}
