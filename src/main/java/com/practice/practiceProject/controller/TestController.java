package com.practice.practiceProject.controller;

import com.practice.practiceProject.entities.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/get")
    public String test(){
        log.debug("Debug log from test controller");
        log.error("Error log from test controller");
        log.info("info log from test controller");
        return "Practice Project";
    }


    //@GetMapping(value = "/user",produces = "application/xml")
    @GetMapping(value = "/user")
    public User getUser() {
        User user = new User();
        user.setId(1);
        user.setEmailId("test@gmail.com");
        user.setFirstName("test");
        return user;
    }
}
