package com.practice.practiceProject.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class TestController {
    @GetMapping("/test")
    public String test(){
        log.debug("Debug log from test controller");
        log.error("Error log from test controller");
        log.info("info log from test controller");
        return "Practice Project";
    }
}
