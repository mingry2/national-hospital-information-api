package com.mk.national_hospital_information.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Response<T> {

    private String resultCode;
    private T result;

    public static <T> Response<T> success(T result) {
        return new Response<>("SUCCESS", result);
    }

    public static Response<ErrorResponse> error(ErrorResponse errorResponse) {
        return new Response<>("ERROR", errorResponse);
    }

}
