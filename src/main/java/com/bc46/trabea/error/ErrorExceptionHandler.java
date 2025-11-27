package com.bc46.trabea.error;

import com.bc46.trabea.error.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ErrorExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessageResponse<Object>> handleResourceNotFoundException(ResourceNotFoundException e) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        Map<String, String> errorsMap = new HashMap<>();

        ErrorMessageResponse<Object> errorMessage = ErrorMessageResponse.builder()
                .status(httpStatus)
                .message(e.getMessage())
                .errors("Resource Not Found")
                .build();

        return ResponseEntity.status(httpStatus).body(errorMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageResponse<Map<String, String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        Map<String, String> errorsMap = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError ->
                errorsMap.put(fieldError.getField(), fieldError.getDefaultMessage()));

        var errorMessage = ErrorMessageResponse.<Map<String, String>>builder()
                .status(httpStatus)
                .message("Input Invalid")
                .errors(errorsMap)
                .build();

        return ResponseEntity.status(httpStatus).body(errorMessage);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorMessageResponse<Object>> handleConflictException(ConflictException e) {
        HttpStatus httpStatus = HttpStatus.CONFLICT;


        ErrorMessageResponse<Object> errorMessage = ErrorMessageResponse.builder()
                .status(httpStatus)
                .message(e.getMessage())
                .errors("Conflict Occured")
                .build();

        return ResponseEntity.status(httpStatus).body(errorMessage);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<ErrorMessageResponse<Object>> handleUnprocessableEntityException(UnprocessableEntityException e) {
        HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;

        ErrorMessageResponse<Object> errorMessage = ErrorMessageResponse.builder()
                .status(httpStatus)
                .message(e.getMessage())
                .errors(List.of(e.getMessage()))
                .build();

        return ResponseEntity.status(httpStatus).body(errorMessage);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessageResponse<Object>> handleIllegalArgumentException(IllegalArgumentException e) {
        HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;

        ErrorMessageResponse<Object> errorMessage = ErrorMessageResponse.builder()
                .status(httpStatus)
                .message(e.getMessage())
                .errors(List.of(e.getMessage()))
                .build();

        return ResponseEntity.status(httpStatus).body(errorMessage);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorMessageResponse<Object>> handleBadCredentialsException(BadCredentialsException e){
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

        ErrorMessageResponse<Object> errorMessage = ErrorMessageResponse.builder()
                .status(httpStatus)
                .message(e.getMessage())
                .errors(List.of(e.getMessage()))
                .build();
        return ResponseEntity.status(httpStatus).body(errorMessage);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorMessageResponse<Object>> handleUsernameNotFoundException(UsernameNotFoundException e){
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

        ErrorMessageResponse<Object> errorMessage = ErrorMessageResponse.builder()
                .status(httpStatus)
                .message(e.getMessage())
                .errors(List.of(e.getMessage()))
                .build();
        return ResponseEntity.status(httpStatus).body(errorMessage);
    }

    @ExceptionHandler(WorkEmailAndPasswordIncorrectException.class)
    public ResponseEntity<ErrorMessageResponse<Object>> WorkEmailAndPasswordIncorrectException(WorkEmailAndPasswordIncorrectException e){
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

        ErrorMessageResponse<Object> errorMessage = ErrorMessageResponse.builder()
                .status(httpStatus)
                .message(e.getMessage())
                .errors(List.of(e.getMessage()))
                .build();
        return ResponseEntity.status(httpStatus).body(errorMessage);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorMessageResponse<Object>> UnauthorizedException(UnauthorizedException e){
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

        ErrorMessageResponse<Object> errorMessage = ErrorMessageResponse.builder()
                .status(httpStatus)
                .message(e.getMessage())
                .errors(List.of(e.getMessage()))
                .build();
        return ResponseEntity.status(httpStatus).body(errorMessage);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorMessageResponse<Object>> handleBadRequestException(BadRequestException e){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ErrorMessageResponse<Object> errorMessage = ErrorMessageResponse.builder()
                .status(httpStatus)
                .message(e.getMessage())
                .errors(List.of(e.getMessage()))
                .build();
        return ResponseEntity.status(httpStatus).body(errorMessage);
    }
}
