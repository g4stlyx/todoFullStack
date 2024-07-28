package com.g4stly.restApi.jwt;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class JwtSecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtTokenService tokenService;

    @Test
    void testPublicEndpoints() throws Exception {
        mockMvc.perform(post("/authenticate")
                .contentType("application/json")
                .content("{\"username\":\"test\", \"password\":\"test\"}"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/swagger-ui/index.html"))
                .andExpect(status().isOk());
    }

    //* this might be wrong. it should expect it to be forbidden since its protected and user didnt log in yet.
    @Test
    @WithMockUser
    void testProtectedEndpoint() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }
}
