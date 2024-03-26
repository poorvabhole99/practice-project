package com.practice.practiceProject.service.serviceImpl;

import com.practice.practiceProject.dto.UserInputDto;
import com.practice.practiceProject.enums.ErrorEnum;
import com.practice.practiceProject.exception.PracticeProjectException;
import com.practice.practiceProject.exception.UserNotFoundException;
import com.practice.practiceProject.repository.UserRepository;
import com.practice.practiceProject.dto.UserDto;
import com.practice.practiceProject.entities.User;
import com.practice.practiceProject.response.ErrorResponse;
import com.practice.practiceProject.response.PracticeProjectResponse;
import com.practice.practiceProject.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    public UserServiceImpl(UserRepository userDao, UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Note
    // During the time of user creation, check no user created with same user email, show proper
    // message for this
    // Save password in encoded format
    // Password length must be of 8
    @Override
    public PracticeProjectResponse createUser(final User user) throws PracticeProjectException {
        log.info("Started create user service method");
        validateEmailIdAndIsActive(user.getEmailId());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        final User newUser = this.userRepository.save(user);

        UserDto userDto = new UserDto();
        userDto.setFirstName(newUser.getFirstName());
        userDto.setEmailId(newUser.getEmailId());
        userDto.setDateOfBirth(newUser.getDateOfBirth());
        userDto.setLastName(newUser.getLastName());
        userDto.setPassword(passwordEncoder.encode(newUser.getPassword()));
        log.info("Completed create user service method");
        return new PracticeProjectResponse("User created successfully", userDto);

    }
    private void validateEmailIdAndIsActive(String emailId) throws PracticeProjectException {
        Optional<User> optionalUser = this.userRepository.findByEmailIdAndIsActive(emailId);
//        User user;
        if (optionalUser.isPresent()){
//            user = optionalUser.get();
            throw new PracticeProjectException(new ErrorResponse(ErrorEnum.USER_EMAIL_ALREADY_EXIST.getErrorMsg(),false, ErrorEnum.USER_EMAIL_ALREADY_EXIST.getErrorCode()));
        }

//        if (userRepository.existsByEmailId(emailId)){
//            throw new PracticeProjectException(new ErrorResponse(ErrorEnum.USER_EMAIL_ALREADY_EXIST.getErrorMsg(),false, ErrorEnum.USER_EMAIL_ALREADY_EXIST.getErrorCode()));
//        }
    }
//    private void validateActiveUser(String emailId) throws PracticeProjectException {
//        Optional<User> optionalUser = this.userRepository.findByEmailId(emailId);
//        User user = null;
//        if (optionalUser.isPresent()){
//            user = optionalUser.get();
//        }else {
//
//        }
//        if (user.getIsActive()){
//            throw new PracticeProjectException(new ErrorResponse("User is active", false, "101-05"));
//        }
//    }

    /**
     * Write API to get details of a single user
     * If user does not exist return proper message
     * */
    @Override
    public PracticeProjectResponse getSingleUser(final String emailId) throws UserNotFoundException {
        final Optional<User> optionalUser = this.userRepository.findByEmailIdAndIsActive(emailId);
//        System.out.println("*********************"+optionalUser.get());
        User user;
        if(optionalUser.isPresent()){
            user = optionalUser.get();
        }else {
            throw new UserNotFoundException(new ErrorResponse(ErrorEnum.USER_NOT_FOUND.getErrorMsg(), false, ErrorEnum.USER_NOT_FOUND.getErrorCode()));
        }

        UserDto userDto = new UserDto();
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setDateOfBirth(user.getDateOfBirth());
        userDto.setEmailId(user.getEmailId());
        return new PracticeProjectResponse("User found", true, userDto);
    }


    /**
     * Write API to delete a user (soft deletion)
     * For this you have to add a new field in User table( like user status)
     * */
    @Override
    public PracticeProjectResponse deleteUser(String emailId) throws UserNotFoundException {
        final Optional<User> optionalUser = this.userRepository.findByEmailIdAndIsActive(emailId);
        User user;
        if(optionalUser.isPresent()){
            user = optionalUser.get();
            user.setIsActive(false);
            this.userRepository.save(user);
        }else {
            throw new UserNotFoundException(new ErrorResponse(ErrorEnum.USER_NOT_FOUND.getErrorMsg(), false, ErrorEnum.USER_NOT_FOUND.getErrorCode()));
        }
        return new PracticeProjectResponse("User Deleted successfully", true);
    }
    /**
     * Write API to update user
     * kept previous scenario in mind, can not update user email
     * */

    @Override
    public PracticeProjectResponse updateUser(String emailId, UserInputDto userInputDto) throws PracticeProjectException {
        final Optional<User> updatedUser = this.userRepository.findByEmailIdAndIsActive(emailId);
        User user = null;
        if (updatedUser.isPresent()){
            user = updatedUser.get();
        }else {
            throw new UserNotFoundException(new ErrorResponse(ErrorEnum.USER_NOT_FOUND.getErrorMsg(), false, ErrorEnum.USER_NOT_FOUND.getErrorCode()));
        }

        user.setFirstName(userInputDto.getFirstName());
        user.setLastName(userInputDto.getLastName());
        user.setDateOfBirth(userInputDto.getDateOfBirth());
        this.userRepository.save(user);

        return new PracticeProjectResponse("User updated successfully", true, user);
    }







}
