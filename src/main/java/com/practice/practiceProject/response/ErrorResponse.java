package com.practice.practiceProject.response;

import lombok.Getter;

import java.util.List;

@Getter

public class ErrorResponse extends BaseResponse{
    private String statusCode;
    private List<String> error;

    public ErrorResponse(String message, Boolean success, String statusCode) {
        super(message, success);
        this.statusCode = statusCode;
    }

    public ErrorResponse(String message, Boolean success, List<String> error){
        super(message, success);
        this.error = error;
    }
}
