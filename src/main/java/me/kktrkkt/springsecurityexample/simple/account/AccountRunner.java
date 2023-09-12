package me.kktrkkt.springsecurityexample.simple.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AccountRunner implements ApplicationRunner {

    @Autowired
    private AccountService accounts;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account user = new Account();
        user.setUsername("user");
        user.setPassword("password");
        user.setRole("USER");
        Account admin = new Account();
        admin.setUsername("admin");
        admin.setPassword("password");
        admin.setRole("ADMIN");
        accounts.createUser(user);
        accounts.createUser(admin);
    }
}
