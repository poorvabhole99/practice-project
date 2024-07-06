package com.practice.practiceProject.controller;

import com.practice.practiceProject.annotation.TrackExecutionTime;
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

  /**
   * Create a new user.
   *
   * @param user the user to be created
   * @return the response entity containing the newly created user
   * @throws PracticeProjectException if an error occurs during user creation
   * @author: Poorva Bhole (implementation)
   */
  @PostMapping("/create")
  @TrackExecutionTime
  public ResponseEntity<PracticeProjectResponse> createUser(@Valid @RequestBody User user)
      throws PracticeProjectException {
    final PracticeProjectResponse practiceProjectResponse = this.userService.createUser(user);
    return new ResponseEntity<>(practiceProjectResponse, HttpStatus.CREATED);
  }

  /**
   * Retrieves a single user based on their email ID.
   *
   * @param emailId the unique email ID of the user to retrieve
   * @return a ResponseEntity containing the response with details of the retrieved user
   * @throws UserNotFoundException if the user with the specified email ID does not exist
   * @author Poorva Bhole (implementation)
   */
  @GetMapping("/getUser/{emailId}")
  @TrackExecutionTime
  public ResponseEntity<PracticeProjectResponse> getSingleUser(@PathVariable String emailId)
      throws UserNotFoundException {
    final PracticeProjectResponse practiceProjectResponse = this.userService.getSingleUser(emailId);
    return new ResponseEntity<>(practiceProjectResponse, HttpStatus.OK);
  }

  @DeleteMapping("/delete/{emailId}")
  @TrackExecutionTime
  public ResponseEntity<PracticeProjectResponse> deleteUser(@PathVariable String emailId)
      throws UserNotFoundException {
    final PracticeProjectResponse practiceProjectResponse = this.userService.deleteUser(emailId);
    log.info("Completed delete user API");
    return new ResponseEntity<>(practiceProjectResponse, HttpStatus.OK);
  }

  @PutMapping("/update/{emailId}")
  @TrackExecutionTime
  public ResponseEntity<PracticeProjectResponse> updateUser(@PathVariable String emailId,
      @Valid @RequestBody UserInputDto userDto) throws PracticeProjectException {
    final PracticeProjectResponse practiceProjectResponse = this.userService.updateUser(emailId,
        userDto);
    return new ResponseEntity<>(practiceProjectResponse, HttpStatus.OK);
  }

  @PutMapping("/activate/{emailId}")
  @TrackExecutionTime
  public ResponseEntity<PracticeProjectResponse> activateUser(@PathVariable String emailId)
      throws UserNotFoundException {
    final PracticeProjectResponse practiceProjectResponse = this.userService.activateUser(emailId);
    log.info("Completed activate user API");
    return new ResponseEntity<>(practiceProjectResponse, HttpStatus.OK);
  }


}
