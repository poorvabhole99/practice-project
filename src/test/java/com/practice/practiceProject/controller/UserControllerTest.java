package com.practice.practiceProject.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.practiceProject.Security.JwtAuthFilter;
import com.practice.practiceProject.TestContainerBase;
import com.practice.practiceProject.entities.User;
import com.practice.practiceProject.repository.UserRepository;
import com.practice.practiceProject.service.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WithMockUser
@SpringBootTest
class UserControllerTest extends TestContainerBase {

  public static final String POST_CREATE_USER = "/user/create";
  @Autowired
  protected WebApplicationContext context;
  @Autowired
  private ObjectMapper objectMapper;
  private MockMvc mockMvc;
  @MockBean
  private JwtAuthFilter jwtAuthFilter;
  @MockBean
  private UserRepository userRepository;
  @MockBean
  private UserServiceImpl userServiceImpl;

  @BeforeEach
  public void setUp() {
    try {
      if (null == mockMvc) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

//  @Test
//  void createUser() throws Exception {
//    User user = new User();
//    user.setId(1);
//    user.setFirstName("");
//    user.setEmailId("test@gmail.com");
//    user.setLastName("testLastName");
//    user.setDateOfBirth("15/05/2000");
//    user.setPassword("password");
//
//
//    final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(POST_CREATE_USER)
//            .content(objectMapper.writeValueAsString(user))
//            .contentType(MediaType.APPLICATION_JSON))
//        .andReturn();
//    Assertions.assertEquals(400, result.getResponse().getStatus(), "Http mismatch");
//    Assertions.assertEquals("First name can't be blank",result.getResponse().getErrorMessage());
////        .andExpect(status().isBadRequest())
////        .andExpect(jsonPath("$.firstName").value("First name can't be blank"));
//  }

  @Test
  void createUser() throws Exception {
    User user = new User();
    user.setId(1);
    user.setFirstName("");
    user.setEmailId("test@gmail.com");
    user.setLastName("testLastName");
    user.setDateOfBirth("15/05/2000");
    user.setPassword("password");

    mockMvc.perform(MockMvcRequestBuilders.post(POST_CREATE_USER)
            .content(objectMapper.writeValueAsString(user))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.firstName").value("First name can't be blank"));

  }

  //Write test cases to check when the last name is blank


  //Write test cases to check when the date of birth is blank


  //Write test cases to check when password is null


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