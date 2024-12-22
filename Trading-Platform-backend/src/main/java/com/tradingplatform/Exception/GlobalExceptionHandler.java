package com.tradingplatform.Exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<CustomExceptionHandler> resourceNotFound(ResourceNotFound ex){
        CustomExceptionHandler userNotFound = CustomExceptionHandler.builder().message("User not found").httpStatus(HttpStatus.NOT_FOUND).localDate(LocalDate.now()).success(false).build();
        return new ResponseEntity<>(userNotFound,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleApiResponseException(MethodArgumentNotValidException ex){
        Map<String,String> error = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((errors)->{
            String fieldName = ((FieldError)errors).getField();
            String errorMessage = errors.getDefaultMessage();
            error.put(fieldName,errorMessage);
        });
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }
}
