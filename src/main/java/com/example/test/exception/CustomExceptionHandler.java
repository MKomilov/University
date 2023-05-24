package com.example.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Customized Exception class for program when exception is occurred
 */
@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(value = {CustomRequestException.class})
    public ResponseEntity<Object> handlerCustomException(CustomRequestException customRequestException) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        CustomException customException = new CustomException(
                customRequestException.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Asia/Tashkent")));
        return new ResponseEntity<>(customException, badRequest);
    }

}
