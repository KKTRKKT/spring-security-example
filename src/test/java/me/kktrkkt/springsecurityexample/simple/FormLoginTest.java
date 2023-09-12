package me.kktrkkt.springsecurityexample.simple;

import me.kktrkkt.springsecurityexample.simple.account.Account;
import me.kktrkkt.springsecurityexample.simple.account.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class FormLoginTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountService accounts;

    @Test
    // 테스트 DB에 적용된 내용을 롤백시켜준다
    @Transactional
    void login_success() throws Exception {
        createAccount("kktrkkt", "password", "USER");
        this.mockMvc.perform(formLogin().user("kktrkkt").password("password"))
                .andExpect(authenticated())
                .andDo(print());
    }

    @Test
    @Transactional
    void login_failure() throws Exception {
        createAccount("kktrkkt", "password", "USER");
        this.mockMvc.perform(formLogin().user("kktrkkt").password("password1"))
                .andExpect(unauthenticated())
                .andDo(print());
    }

    private Account createAccount(String username, String password, String role) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setRole(role);
        return accounts.createUser(account);
    }
}
