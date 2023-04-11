package it.aleph.omega.controller;

import it.aleph.omega.error.*;
import it.aleph.omega.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@ResponseBody
public class CatalogControllerAdvice {

    private final static String MESSAGE_FORMAT = "%s : %s";
    private final static String MESSAGE_CONSTRAINT_VIOLATIONS = "Some constraints violations occurred.";
    private final static String MESSAGE_RESOURCE_NOT_FOUND = "Resource not found";



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiError handleConstraintViolations(MethodArgumentNotValidException ex, HttpServletRequest request) {
        return ApiError.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(Instant.now())
                .path(request.getRequestURL().toString())
                .message(MESSAGE_CONSTRAINT_VIOLATIONS)
                .errors(ex.getFieldErrors().stream()
                        .map(fieldError -> ErrorMessage.builder()
                                .message(String.format(MESSAGE_FORMAT,
                                        fieldError.getField(),
                                        fieldError.getDefaultMessage()))
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    public ApiError handleResourceNotFound(NotFoundException ex, HttpServletRequest request){
        return ApiError.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(Instant.now())
                .path(request.getRequestURL().toString())
                .message(MESSAGE_RESOURCE_NOT_FOUND)
                .errors(List.of(ErrorMessage.builder().message(ex.getMessage()).build()))
                .build();
    }
}
