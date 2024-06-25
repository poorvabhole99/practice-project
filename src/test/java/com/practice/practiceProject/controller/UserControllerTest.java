package com.practice.practiceProject.controller;

import com.practice.practiceProject.TestContainerBase;
import com.practice.practiceProject.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Testcontainers
class UserControllerTest extends TestContainerBase {


    public static final String POST_CREATE_USER = "/user/create";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private SecurityFilterChain securityFilterChain;
    @Test
    void createUser() throws Exception {
        User user = new User();
        user.setFirstName("");
        user.setEmailId("test@gmail.com");
        user.setLastName("testLastName");
        user.setDateOfBirth("15/05/2000");
        user.setPassword("password");
        mockMvc.perform(MockMvcRequestBuilders.get(POST_CREATE_USER)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.firstName").value("First name can't be blank"));
    }

    @Test
    void getSingleUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void updateUser() {
    }
}