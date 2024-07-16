package com.practice.practiceProject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.practiceProject.Security.JwtAuthFilter;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.MySQLContainer;

@Log4j2
@WithMockUser
public class TestContainerBase {
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.37");

//    @Autowired
//    protected WebApplicationContext context;
//    @Autowired
//    protected ObjectMapper objectMapper;
//    @Autowired
//    protected MockMvc mockMvc;
//    @MockBean
//    protected JwtAuthFilter jwtAuthFilter;
    @BeforeAll
    static void beforeAll() {
        log.info("********** BEFORE ALL ************");
        mysql.start();
    }

    @AfterAll
    static void afterAll() {
        log.info("********** AFTER ALL ************");
        mysql.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }
}
