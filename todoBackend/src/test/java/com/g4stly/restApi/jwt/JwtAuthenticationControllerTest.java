// package com.g4stly.restApi.jwt;

// import static org.mockito.Mockito.*;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.test.web.servlet.MockMvc;

// import java.util.Collections;

// @WebMvcTest(JwtAuthenticationController.class)
// class JwtAuthenticationControllerTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @MockBean
//     private JwtTokenService tokenService;

//     @MockBean
//     private AuthenticationManager authenticationManager;

//     @BeforeEach
//     void setUp() {
//         when(tokenService.generateToken(any(Authentication.class))).thenReturn("test-token");
//     }

//     @Test
//     void testGenerateToken() throws Exception {
//         JwtTokenRequest jwtTokenRequest = new JwtTokenRequest("testuser", "testpassword");

//         Authentication authentication = new UsernamePasswordAuthenticationToken(
//                 jwtTokenRequest.username(),
//                 jwtTokenRequest.password(),
//                 Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
//         );

//         when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
//                 .thenReturn(authentication);

//         mockMvc.perform(post("/authenticate")
//                 .contentType("application/json")
//                 .content("{\"username\":\"testuser\", \"password\":\"testpassword\"}"))
//                 .andExpect(status().isOk());
//     }
// }
package com.g4stly.restApi.jwt;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

@WebMvcTest(JwtAuthenticationController.class)
@Import(JwtSecurityConfig.class)
class JwtAuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtTokenService tokenService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
        when(tokenService.generateToken(any(Authentication.class))).thenReturn("test-token");
    }

    @Test
    void testGenerateToken() throws Exception {
        JwtTokenRequest jwtTokenRequest = new JwtTokenRequest("testuser", "testpassword");

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                jwtTokenRequest.username(),
                jwtTokenRequest.password(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        mockMvc.perform(post("/authenticate")
                .contentType("application/json")
                .content("{\"username\":\"testuser\", \"password\":\"testpassword\"}"))
                .andExpect(status().isOk());
    }
}
