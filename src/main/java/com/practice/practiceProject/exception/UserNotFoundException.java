package com.practice.practiceProject.exception;

import com.practice.practiceProject.response.ErrorResponse;
import lombok.Getter;

@Getter
public class UserNotFoundException extends PracticeProjectException{

    public UserNotFoundException(ErrorResponse response) {
        super(response);
    }
}
