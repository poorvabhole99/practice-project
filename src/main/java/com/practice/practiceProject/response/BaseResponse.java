package com.practice.practiceProject.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse implements Serializable {
    private String message;
    private Boolean success;


    public BaseResponse(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }

    public BaseResponse(String message) {
        this.message = message;
    }

}
