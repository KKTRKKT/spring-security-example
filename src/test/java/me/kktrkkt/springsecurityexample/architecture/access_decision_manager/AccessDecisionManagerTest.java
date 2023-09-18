package me.kktrkkt.springsecurityexample.architecture.access_decision_manager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import(SecurityConfig.class)
class AccessDecisionManagerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "USER")
    void userToAdmin() throws Exception {
        this.mockMvc.perform(get("/user"))
                .andExpect(status().isNotFound())
                .andDo(print());

        this.mockMvc.perform(get("/admin"))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminToUser() throws Exception {
        this.mockMvc.perform(get("/admin"))
                .andExpect(status().isNotFound())
                .andDo(print());

        this.mockMvc.perform(get("/user"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


}