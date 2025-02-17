package com.mk.national_hospital_information.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    // user
    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "DUPLICATED USERNAME"),
    ID_NOT_FOUND(HttpStatus.NOT_FOUND, "ID NOT FOUND"),
    USERNAME_NOT_FOUND(HttpStatus.NOT_FOUND, "USERNAME NOT FOUND"),

    // hospital
    HOSPITAL_NOT_FOUND(HttpStatus.NOT_FOUND, "HOSPITAL NOT FOUND"),

    // review
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW NOT FOUND"),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "INVALID PERMISSION"),

    // comment
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT NOT FOUND"),

    ;

    private final HttpStatus status;
    private final String message;

}
