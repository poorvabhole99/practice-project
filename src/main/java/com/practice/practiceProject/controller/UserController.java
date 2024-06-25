package com.practice.practiceProject.controller;

import com.practice.practiceProject.dto.UserInputDto;
import com.practice.practiceProject.entities.User;
import com.practice.practiceProject.exception.PracticeProjectException;
import com.practice.practiceProject.exception.UserNotFoundException;
import com.practice.practiceProject.response.PracticeProjectResponse;
import com.practice.practiceProject.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
@Log4j2
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<PracticeProjectResponse> createUser(@Valid @RequestBody  User user) throws PracticeProjectException {
        log.info("Started create user API ");
        final PracticeProjectResponse practiceProjectResponse = this.userService.createUser(user);
        log.info("Completed create user API");
        return new ResponseEntity<>(practiceProjectResponse, HttpStatus.CREATED);
    }

    @GetMapping("/getUser/{emailId}")
    public ResponseEntity<PracticeProjectResponse> getSingleUser(@PathVariable String emailId) throws UserNotFoundException {
        log.info("Started get single user API, emailId :{}",emailId);
        final PracticeProjectResponse practiceProjectResponse = this.userService.getSingleUser(emailId);
        log.info("Completed get single user API, emailId :{}",emailId);
        return new ResponseEntity<>(practiceProjectResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{emailId}")
    public ResponseEntity<PracticeProjectResponse> deleteUser(@PathVariable String emailId) throws UserNotFoundException {
        final PracticeProjectResponse practiceProjectResponse = this.userService.deleteUser(emailId);
        log.info("Completed delete user API");
        return new ResponseEntity<>(practiceProjectResponse, HttpStatus.OK);
    }

    @PutMapping("/update/{emailId}")
    public ResponseEntity<PracticeProjectResponse> updateUser(@PathVariable String emailId, @RequestBody UserInputDto userDto) throws PracticeProjectException {
        final PracticeProjectResponse practiceProjectResponse = this.userService.updateUser(emailId, userDto);
        log.info("Completed update API");
        return new ResponseEntity<>(practiceProjectResponse, HttpStatus.OK);
    }


}
