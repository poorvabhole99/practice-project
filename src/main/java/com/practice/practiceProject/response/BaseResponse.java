package com.practice.practiceProject.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse implements Serializable {
    public String message;
    public Boolean success;


    public BaseResponse(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }

    public BaseResponse(String message) {
        this.message = message;
    }

    public BaseResponse(Boolean success) {
        this.success = success;
    }

}
