package com.leveling.core_system.data.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.leveling.core_system.data.dto.ApiResponse;

@RestControllerAdvice 
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Object>> handleExcption(ApiException apiException){
        ApiResponse<Object> response = ApiResponse.builder()
        .success(false)
        .message(apiException.getMessage())
        .data(null)
        .build();

        return ResponseEntity.status(apiException.getStatus()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleUnknown(Exception exception){
        ApiResponse<Object> response = ApiResponse.builder()
        .success(false)
        .message("Something wrong with the server!")
        .data(null)
        .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
