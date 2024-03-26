package com.practice.practiceProject.exception;

import com.practice.practiceProject.response.ErrorResponse;
import lombok.Getter;
@Getter
public class PracticeProjectException extends Exception{
    private final ErrorResponse errorResponse;
    public PracticeProjectException(ErrorResponse response){
        this.errorResponse = response;
    }
}
