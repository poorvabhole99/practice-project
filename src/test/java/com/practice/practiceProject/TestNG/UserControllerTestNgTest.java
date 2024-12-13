package com.practice.practiceProject.TestNG;

import com.practice.practiceProject.PracticeProjectApplication;
import com.practice.practiceProject.TestContainerBase;
import com.practice.practiceProject.constant.MessageConstant;
import com.practice.practiceProject.dto.UserDto;
import com.practice.practiceProject.response.PracticeProjectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WithMockUser
//        (classes = PracticeProjectApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserControllerTestNgTest extends TestContainerBase {
    public static final String GET_SINGLE_USER = "/user/getUser/poorva@gmail.com";

//    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AbstractTestNGSpringContextTests abstractTestNGSpringContextTests;

    private final AbstractTestNGSpringContextTests springContextDelegate = new AbstractTestNGSpringContextTests() {};

    @BeforeClass
    public void setUpSpringContext() throws Exception {
        springContextDelegate.springTestContextPrepareTestInstance();
    }
    @BeforeClass
    public void setUp() {
        try {
            if (null == mockMvc) {
                mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void getSingleUser_positiveScenario() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(GET_SINGLE_USER))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userDto.emailId").value("test@gmail.com"));
    }
}
