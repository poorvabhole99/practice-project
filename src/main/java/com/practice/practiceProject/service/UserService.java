package com.practice.practiceProject.service;

import com.practice.practiceProject.dto.UserInputDto;
import com.practice.practiceProject.entities.User;
import com.practice.practiceProject.exception.PracticeProjectException;
import com.practice.practiceProject.exception.UserNotFoundException;
import com.practice.practiceProject.response.PracticeProjectResponse;

public interface UserService {
    PracticeProjectResponse createUser(User user) throws PracticeProjectException;
    PracticeProjectResponse getSingleUser(String emailId) throws UserNotFoundException;

    PracticeProjectResponse deleteUser(String emailId) throws UserNotFoundException;

    PracticeProjectResponse updateUser(String emailId, UserInputDto user) throws PracticeProjectException;
}
