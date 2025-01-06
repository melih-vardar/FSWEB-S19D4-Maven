package com.workintech.s19d1.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // response olarak bir obje donerken ayni zamanda genel httpstatus bilgisini de ResponseEntity<> sayesinde veririz.
    // 22 ve 23. satirlarda göründüğü gibi getHttpStatus'u hem response objesinin içinde hem de dışında veriyoruz.

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(ApiException apiException) {
        log.error("api exception occured: " + apiException);
        ExceptionResponse exceptionResponse = new ExceptionResponse(apiException.getMessage(), apiException.getHttpStatus().value(), LocalDateTime.now());
        return new ResponseEntity<>(exceptionResponse, apiException.getHttpStatus());
    }

    // handle edemediğim durumlar için bir exceptionHandler olduğundan dolayı internal server error hatası verdiriyoruz. Get kullanamadığımız için.

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(Exception exception) {
        log.error("exception occured: " + exception);
        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}