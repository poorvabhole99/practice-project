package com.practice.practiceProject.response;

import com.practice.practiceProject.dto.UserDto;
import com.practice.practiceProject.entities.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PracticeProjectResponse extends BaseResponse{
    private  UserDto userDto;
    private  String statusCode;
    private User user;

    public PracticeProjectResponse(String message, UserDto userDto){
        super(message);
        this.userDto=userDto;
    }
    public PracticeProjectResponse(String message, Boolean status, UserDto userDto){
        super(message, status);
        this.userDto=userDto;
    }
    public PracticeProjectResponse(String message, Boolean status, User user){
        super(message, status);
        this.user=user;
    }


    public PracticeProjectResponse(String message, boolean status) {
        super(message, status);
    }
}
