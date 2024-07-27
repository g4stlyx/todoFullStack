// package com.g4stly.restApi.jwt;

// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.security.test.context.support.WithMockUser;
// import org.springframework.test.web.servlet.MockMvc;

// @SpringBootTest
// @AutoConfigureMockMvc
// class JwtSecurityConfigTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @MockBean
//     private JwtTokenService tokenService;

//     @Test
//     void testPublicEndpoints() throws Exception {
//         mockMvc.perform(post("/authenticate")
//                 .contentType("application/json")
//                 .content("{\"username\":\"testuser\", \"password\":\"testpassword\"}"))
//                 .andExpect(status().isOk());

//         mockMvc.perform(get("/v3/api-docs"))
//                 .andExpect(status().isOk());

//         mockMvc.perform(get("/swagger-ui/index.html"))
//                 .andExpect(status().isOk());
//     }

//     @Test
//     @WithMockUser
//     void testProtectedEndpoint() throws Exception {
//         mockMvc.perform(get("/protected-endpoint"))
//                 .andExpect(status().isForbidden());
//     }
// }

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
                .content("{\"username\":\"g4stly\", \"password\":\"pwd\"}"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/swagger-ui/index.html"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testProtectedEndpoint() throws Exception {
        mockMvc.perform(get("/users/"))
                .andExpect(status().isForbidden());
    }
}
