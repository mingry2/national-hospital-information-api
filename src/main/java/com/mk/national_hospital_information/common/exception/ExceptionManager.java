package com.mk.national_hospital_information.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionManager {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<?> appExceptionHandler(GlobalException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), e.getMessage());

        return ResponseEntity
            .status(e.getErrorCode().getStatus())
            .body(Response.error(errorResponse));
    }

}
