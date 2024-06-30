package com.practice.practiceProject.enums;

import lombok.Getter;

/**
 * The enum Error enum.
 */
@Getter
public enum ErrorEnum {

    USER_NOT_FOUND("101-02","User with this email id not found"),

    USER_DELETED("101-03", "User deleted successfully"),

    USER_EMAIL_ALREADY_EXIST("101-04","User with emailId already exist"),

    USER_EMAIL_DEACTIVATED("101-05","Account for this emailId is deactivated, Do you want to activate it?");

    private final String errorCode;
    private final String errorMsg;

    ErrorEnum(final String errorCode, final String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
}
