package com.practice.practiceProject.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.practiceProject.Security.JwtAuthFilter;
import com.practice.practiceProject.Security.JwtAuthenticationEntryPoint;
import com.practice.practiceProject.Security.JwtHelper;
import com.practice.practiceProject.Security.UserDetailsServiceImpl;
import com.practice.practiceProject.TestContainerBase;
import com.practice.practiceProject.entities.User;
import com.practice.practiceProject.repository.UserRepository;
import com.practice.practiceProject.service.UserService;
import com.practice.practiceProject.service.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@WebMvcTest(UserController.class)
class UserControllerTest extends TestContainerBase {
  public static final String POST_CREATE_USER = "/user/create";
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private PasswordEncoder passwordEncoder;
  @MockBean
  private AuthenticationProvider authenticationProvider;
  @MockBean
  private AuthenticationManager authenticationManager;
  @MockBean
  private SecurityFilterChain securityFilterChain;
  @MockBean
  private JwtAuthFilter jwtAuthFilter;
  @MockBean
  private UserDetailsServiceImpl userDetailsService;
  @MockBean
  private JwtHelper jwtHelper;
  @MockBean
  private UserRepository userRepository;

  @MockBean
  private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  @MockBean
  private UserServiceImpl userServiceImpl;

  @Autowired
  private UserController userController;

  @Test
  void createUser() throws Exception {
    User user = new User();
    user.setId(1);
    user.setFirstName(" ");
    user.setEmailId("test@gmail.com");
    user.setLastName("testLastName");
    user.setDateOfBirth("15/05/2000");
    user.setPassword("password");
    mockMvc.perform(MockMvcRequestBuilders.post(POST_CREATE_USER)
            .with(csrf())
            .content(objectMapper.writeValueAsString(user))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
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