package com.practice.practiceProject.response;

import com.practice.practiceProject.entities.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class UserResponse extends BaseResponse{
    private List<User> userList;

    public UserResponse(List<User> userList, Boolean success) {
        super(success);
        this.userList = userList;
    }
}
