package com.practice.practiceProject.service.serviceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.practice.practiceProject.TestContainerBase;
import com.practice.practiceProject.constant.MessageConstant;
import com.practice.practiceProject.dto.UserInputDto;
import com.practice.practiceProject.entities.User;
import com.practice.practiceProject.enums.ErrorEnum;
import com.practice.practiceProject.exception.PracticeProjectException;
import com.practice.practiceProject.exception.UserNotFoundException;
import com.practice.practiceProject.repository.UserRepository;
import com.practice.practiceProject.response.PracticeProjectResponse;
import com.practice.practiceProject.service.UserService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class UserServiceImplTest extends TestContainerBase {

  @Autowired
  private UserService userService;

  @MockBean
  private UserRepository userRepository;

  @Test
  void createUser() throws PracticeProjectException {
    User user = new User();
    user.setFirstName("testFirstName");
    user.setEmailId("test@gmail.com");
    user.setLastName("testLastName");
    user.setDateOfBirth("15/05/2000");
    user.setPassword("password");
    when(userRepository.findByEmailIdAndIsActive("test@gmail.com")).thenReturn(Optional.empty());
    when(userRepository.save(user)).thenReturn(user);
    PracticeProjectResponse response = userService.createUser(user);
    assertThat(response.getMessage()).isEqualTo("User created successfully");
  }

  @Test
  void createUser_UserIsAlreadyExistAndActive(){
    final String emailId = "test@gmail.com";
    User user = new User();
    user.setFirstName("testFirstName");
    user.setEmailId("test@gmail.com");
    user.setLastName("testLastName");
    user.setDateOfBirth("15/05/2000");
    user.setPassword("password");

    when(userRepository.findByEmailIdAndIsActive(emailId)).thenReturn(Optional.of(user));

    PracticeProjectException exception = assertThrows(PracticeProjectException.class, () ->
            userService.createUser(user));

    assertThat(exception.getErrorResponse().getMessage()).isEqualTo(ErrorEnum.USER_EMAIL_ALREADY_EXIST.getErrorMsg());
    assertThat(exception.getErrorResponse().getStatusCode()).isEqualTo(ErrorEnum.USER_EMAIL_ALREADY_EXIST.getErrorCode());
    assertThat(exception.getErrorResponse().getSuccess()).isFalse();
  }

  @Test
  void getSingleUser_UserNotFound_NegativeScenario() {
    final String emailId = "test@gmail.com";
    when(userRepository.findByEmailIdAndIsActive(emailId)).thenReturn(Optional.empty());
    UserNotFoundException exception = assertThrows(UserNotFoundException.class, () ->
            userService.getSingleUser(emailId));
    assertThat(exception.getErrorResponse().getMessage()).isEqualTo(
            ErrorEnum.USER_NOT_FOUND.getErrorMsg());
    assertThat(exception.getErrorResponse().getStatusCode()).isEqualTo(
            ErrorEnum.USER_NOT_FOUND.getErrorCode());
    assertThat(exception.getErrorResponse().getSuccess()).isEqualTo(false);
  }

  @Test
  void getSingleUser_UserFound_UserActive_PositiveScenario() throws UserNotFoundException {
    User user = new User();
    user.setIsActive(true);
    user.setPassword("testPassword");
    user.setFirstName("testFirstName");
    user.setLastName("testLastName");
    final String emailId = "test@gmail.com";
    when(userRepository.findByEmailIdAndIsActive(emailId)).thenReturn(Optional.of(user));

    PracticeProjectResponse response = userService.getSingleUser(emailId);
    assertThat(response.getMessage()).isEqualTo(MessageConstant.USER_FOUND_SUCCESS);
  }


  @Test
  void deleteUser_UserNotFound() {
    final String emailId = "test@gmail.com";
    when(userRepository.findByEmailIdAndIsActive(emailId)).thenReturn(Optional.empty());
    UserNotFoundException userNotFoundException = assertThrows(UserNotFoundException.class, () ->
            userService.deleteUser(emailId));
    assertThat(userNotFoundException.getErrorResponse().getMessage()).isEqualTo(
            ErrorEnum.USER_NOT_FOUND.getErrorMsg());
    assertThat(userNotFoundException.getErrorResponse().getStatusCode()).isEqualTo(
            ErrorEnum.USER_NOT_FOUND.getErrorCode());
    assertThat(userNotFoundException.getErrorResponse().getSuccess()).isEqualTo(false);
  }

  @Test
  void deleteUser_UserFound() throws UserNotFoundException {
    User user = new User();
    user.setIsActive(true);
    user.setPassword("testPassword");
    user.setFirstName("testFirstName");
    user.setLastName("testLastName");
    final String emailId = "test@gmail.com";
    when(userRepository.findByEmailIdAndIsActive(emailId)).thenReturn(Optional.of(user));
    when(userRepository.save(user)).thenReturn(user);
    PracticeProjectResponse response = userService.deleteUser(emailId);
    assertThat(response.getMessage()).isEqualTo(MessageConstant.USER_DELETED_SUCCESS);
    assertThat(response.getSuccess()).isEqualTo(true);
  }



  @Test
  void updateUser_UserNotFound() {
    final String emailId = "test@gmail.com";
    UserInputDto userInputDto = new UserInputDto();
    userInputDto.setFirstName("testFirstName");
    userInputDto.setLastName("testLastName");
    userInputDto.setDateOfBirth("testDateOfBirth");
    when(userRepository.findByEmailIdAndIsActive(emailId)).thenReturn(Optional.empty());
    UserNotFoundException userNotFoundException = assertThrows(UserNotFoundException.class, () ->
            userService.updateUser(emailId,userInputDto));
    assertThat(userNotFoundException.getErrorResponse().getMessage()).isEqualTo(
            ErrorEnum.USER_NOT_FOUND.getErrorMsg());
    assertThat(userNotFoundException.getErrorResponse().getStatusCode()).isEqualTo(
            ErrorEnum.USER_NOT_FOUND.getErrorCode());
    assertThat(userNotFoundException.getErrorResponse().getSuccess()).isEqualTo(false);
  }

  @Test
  void updateUser_UserExist() throws PracticeProjectException {
    final String emailId = "test@gmail.com";
    final User user = new User();
    user.setId(1);
    user.setEmailId("test@gmail.com");
    UserInputDto userInputDto = new UserInputDto();
    userInputDto.setFirstName("testFirstName");
    userInputDto.setLastName("testLastName");
    userInputDto.setDateOfBirth("testDateOfBirth");
    when(userRepository.findByEmailIdAndIsActive(emailId)).thenReturn(Optional.of(user));
    when(userRepository.save(user)).thenReturn(null);
    PracticeProjectResponse response = this.userService.updateUser(emailId,userInputDto);
    assertThat(response.getMessage()).isEqualTo(MessageConstant.USER_UPDATED_SUCCESS);
    assertThat(response.getSuccess()).isEqualTo(true);

  }
}