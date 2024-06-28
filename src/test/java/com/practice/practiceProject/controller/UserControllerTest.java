package com.practice.practiceProject.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.practiceProject.Security.JwtAuthFilter;
import com.practice.practiceProject.TestContainerBase;
import com.practice.practiceProject.constant.MessageConstant;
import com.practice.practiceProject.dto.UserDto;
import com.practice.practiceProject.dto.UserInputDto;
import com.practice.practiceProject.entities.User;
import com.practice.practiceProject.enums.ErrorEnum;
import com.practice.practiceProject.exception.UserNotFoundException;
import com.practice.practiceProject.repository.UserRepository;
import com.practice.practiceProject.response.ErrorResponse;
import com.practice.practiceProject.response.PracticeProjectResponse;
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
    public static final String GET_SINGLE_USER = "/user/getUser/poorva@gmail.com";
    public static final String DELETE_USER = "/user/delete/test@gmail.com";
    public static final String UPDATE_USER = "/user/update/test@gmail.com";
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
    void invalidCreateUser_blankFirstName() throws Exception {
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
    @Test
    void invalidCreateUser_blankLastName() throws Exception {
        User user = new User();
        user.setId(1);
        user.setFirstName("testFirstName");
        user.setEmailId("test@gmail.com");
        user.setLastName("");
        user.setDateOfBirth("15/05/2000");
        user.setPassword("password");

        mockMvc.perform(MockMvcRequestBuilders.post(POST_CREATE_USER)
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.lastName").value("Last name can't be blank"));
    }


    //Write test cases to check when the date of birth is blank
    @Test
    void invalidCreateUser_blankDateOfBirth() throws Exception {
        User user = new User();
        user.setId(1);
        user.setFirstName("testFirstName");
        user.setEmailId("test@gmail.com");
        user.setLastName("testLastName");
        user.setDateOfBirth("");
        user.setPassword("password");

        mockMvc.perform(MockMvcRequestBuilders.post(POST_CREATE_USER)
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.dateOfBirth").value("Date of birth can't be blank"));
    }


    //Write test cases to check when password is null
    @Test
    void invalidCreateUser_nullPassword() throws Exception {
        User user = new User();
        user.setId(1);
        user.setFirstName("testFirstName");
        user.setEmailId("test@gmail.com");
        user.setLastName("testLastName");
        user.setDateOfBirth("15/05/2000");
        user.setPassword("");

        mockMvc.perform(MockMvcRequestBuilders.post(POST_CREATE_USER)
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password").value("Password can't be blank"));
    }

    @Test
    void invalidCreateUser_invalidEmailId() throws Exception {
        User user = new User();
        user.setId(1);
        user.setFirstName("testFirstName");
        user.setEmailId("dkjnfoisd");
        user.setLastName("testLastName");
        user.setDateOfBirth("15/05/2000");
        user.setPassword("Password");

        mockMvc.perform(MockMvcRequestBuilders.post(POST_CREATE_USER)
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.emailId").value("Email Id is invalid"));
    }

    @Test
    void validCreateUser() throws Exception {
        User user = new User();
        user.setId(1);
        user.setFirstName("testFirstName");
        user.setEmailId("test@gmail.com");
        user.setLastName("testLastName");
        user.setDateOfBirth("15/05/2000");
        user.setPassword("password");

        UserDto userDto = new UserDto();
        userDto.setFirstName("testFirstName");
        userDto.setEmailId("test@gmail.com");
        userDto.setLastName("testLastName");
        userDto.setDateOfBirth("15/05/2000");
        userDto.setPassword("password");
        PracticeProjectResponse practiceProjectResponse = new PracticeProjectResponse("User created successfully", userDto);

        when(userServiceImpl.createUser(user)).thenReturn(practiceProjectResponse);


        mockMvc.perform(MockMvcRequestBuilders.post(POST_CREATE_USER)
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("User created successfully"))
                .andExpect(jsonPath("$.userDto.firstName").value("testFirstName"));
    }


    @Test
    void getSingleUser_InPositiveScenario() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setFirstName("testFirstName");
        userDto.setEmailId("test@gmail.com");
        userDto.setLastName("testLastName");
        userDto.setDateOfBirth("15/05/2000");
        userDto.setPassword("password");

        PracticeProjectResponse practiceProjectResponse = new PracticeProjectResponse(MessageConstant.USER_FOUND_SUCCESS, true, userDto);
        when(userServiceImpl.getSingleUser("poorva@gmail.com")).thenReturn(practiceProjectResponse);
        mockMvc.perform(MockMvcRequestBuilders.get(GET_SINGLE_USER))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userDto.emailId").value("test@gmail.com"));
    }

    @Test
    void getSingleUser_NegativeScenario() throws Exception {
        when(userServiceImpl.getSingleUser("poorva@gmail.com")).thenThrow(new UserNotFoundException(new ErrorResponse(
                ErrorEnum.USER_NOT_FOUND.getErrorMsg(), false, ErrorEnum.USER_NOT_FOUND.getErrorCode())));
        mockMvc.perform(MockMvcRequestBuilders.get(GET_SINGLE_USER))
                .andExpect(status().isNotFound());

    }

    //test case fail, got 404 expected 200
    @Test
    void deleteUser_positiveScenario() throws Exception {
        User user = new User();
        user.setId(1);
        user.setFirstName("testFirstName");
        user.setEmailId("test@gmail.com");
        user.setLastName("testLastName");
        user.setDateOfBirth("15/05/2000");
        user.setPassword("password");

        PracticeProjectResponse practiceProjectResponse = new PracticeProjectResponse(MessageConstant.USER_DELETED_SUCCESS, true);
        when(userServiceImpl.deleteUser("test@gmail.com")).thenReturn(practiceProjectResponse);
        mockMvc.perform(MockMvcRequestBuilders.delete(DELETE_USER))
                .andExpect(status().isOk());

    }

    @Test
    void deleteUser_negativeScenario() throws Exception {
        when(userServiceImpl.deleteUser("test@gmail.com")).thenThrow(new UserNotFoundException(new ErrorResponse(ErrorEnum.USER_NOT_FOUND.getErrorMsg(), false, ErrorEnum.USER_NOT_FOUND.getErrorCode())));
        mockMvc.perform(MockMvcRequestBuilders.delete(DELETE_USER))
                .andExpect(status().isNotFound());

    }

    //test case fail, got 404 expected 200
    @Test
    void updateUser_positiveScenario() throws Exception {
        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setFirstName("testFirstName");
        userInputDto.setLastName("testLastName");
        userInputDto.setDateOfBirth("15/05/2000");

        User user = new User();
        user.setId(1);
        user.setFirstName("testFirstName");
        user.setEmailId("test@gmail.com");
        user.setLastName("testLastName");
        user.setDateOfBirth("15/05/2000");
        user.setPassword("password");

        PracticeProjectResponse practiceProjectResponse = new PracticeProjectResponse(MessageConstant.USER_UPDATED_SUCCESS, true, user);
        when(userServiceImpl.updateUser("test@gmail.com", userInputDto)).thenReturn(practiceProjectResponse);
        mockMvc.perform(MockMvcRequestBuilders.put(UPDATE_USER)
                        .content(objectMapper.writeValueAsString(userInputDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User updated successfully."));

    }

    @Test
    void updateUser_negativeScenario() throws Exception {
        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setFirstName("testFirstName");
        userInputDto.setLastName("testLastName");
        userInputDto.setDateOfBirth("15/05/2000");

        when(userServiceImpl.updateUser("test@gmail.com", userInputDto)).thenThrow(new UserNotFoundException(new ErrorResponse(ErrorEnum.USER_NOT_FOUND.getErrorMsg(), false, ErrorEnum.USER_NOT_FOUND.getErrorCode())));
        mockMvc.perform(MockMvcRequestBuilders.delete(UPDATE_USER))
                .andExpect(status().isNotFound());

    }

    //expected 400 got 404 user not found
    @Test
    void updateUser_negativeScenario_blankFirstName() throws Exception {
        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setFirstName("");
        userInputDto.setLastName("testLastName");
        userInputDto.setDateOfBirth("15/05/2000");

        User user = new User();
        user.setId(1);
        user.setFirstName("testFirstName");
        user.setEmailId("test@gmail.com");
        user.setLastName("testLastName");
        user.setDateOfBirth("15/05/2000");
        user.setPassword("password");

        PracticeProjectResponse practiceProjectResponse = new PracticeProjectResponse(MessageConstant.USER_FOUND_SUCCESS, true, user);
        when(userServiceImpl.updateUser("test@gmail.com", userInputDto)).thenReturn(practiceProjectResponse);
        mockMvc.perform(MockMvcRequestBuilders.put(UPDATE_USER))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.firstName").value("First Name can't be less than 3 letters"));


    }


}