package com.mk.national_hospital_information.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GlobalException extends RuntimeException {

    private ErrorCode errorCode;
    private String message;

}
