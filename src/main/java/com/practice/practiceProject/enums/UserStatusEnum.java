package com.practice.practiceProject.enums;

import lombok.Getter;

@Getter
public enum UserStatusEnum {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private final String value;

    UserStatusEnum(String value) {
        this.value = value;
    }


}
