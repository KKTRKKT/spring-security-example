package me.kktrkkt.springsecurityexample.simple;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SimpleControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Test
    // 익명 사용자 테스트
    @WithAnonymousUser
    void main_anonymous() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    // 인증 유저 테스트
    @WithUser
    void main_user() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("user")))
                .andDo(print());
    }

    @Test
    @WithAnonymousUser
    void dashboard_anonymous() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/dashboard"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"))
                .andDo(print());
    }

    @Test
    @WithUser
    void dashboard_user() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/dashboard"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithAdmin
    void admin_admin() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/admin"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithUser
    void admin_user() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/admin"))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

}