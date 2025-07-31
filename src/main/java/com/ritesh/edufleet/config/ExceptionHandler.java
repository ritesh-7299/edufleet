package com.ritesh.edufleet.config;

import com.ritesh.edufleet.exception.BadRequestException;
import com.ritesh.edufleet.exception.ResourceNotFoundException;
import com.ritesh.edufleet.system.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Arrays;

@RestControllerAdvice
@Slf4j
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleUnreadable(HttpMessageNotReadableException ex, HandlerMethod handlerMethod, HttpServletRequest request) {
        boolean shouldValidate = Arrays.stream(handlerMethod.getMethodParameters())
                .anyMatch(param ->
                        param.hasParameterAnnotation(Valid.class) &&
                                param.hasParameterAnnotation(RequestBody.class)
                );

        if (shouldValidate) {
            log.error("Error {}", ex.getMessage());
            ErrorResponse err = new ErrorResponse("Validation failed: Request body missing or malformed.", request.getRequestURI(), HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
        }

        // For other cases, maybe rethrow or handle differently
        ErrorResponse err = new ErrorResponse("Unexpected request body error.", request.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles not found exception
     *
     * @param ex
     * @param request
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> notFound(ResourceNotFoundException ex, HttpServletRequest request) {
        ErrorResponse err = new ErrorResponse(ex.getMessage(), request.getRequestURI(), HttpStatus.NOT_FOUND.value());
        log.error(err.toString());
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    /**
     * Handled not found exceptions
     *
     * @param ex
     * @param request
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> noResourceFound(NoResourceFoundException ex, HttpServletRequest request) {
        ErrorResponse err = new ErrorResponse("Not found", request.getRequestURI(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }


    /**
     * Handles custom bad request exception
     *
     * @param ex
     * @param request
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> badRequest(BadRequestException ex, HttpServletRequest request) {
        ErrorResponse err = new ErrorResponse(ex.getMessage(), request.getRequestURI(), HttpStatus.BAD_REQUEST.value());
        log.error(err.toString());
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }


    /**
     * Handles validation errors
     *
     * @param ex
     * @param request
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validationError(MethodArgumentNotValidException ex, HttpServletRequest request) {
        StringBuilder errors = new StringBuilder();

        ex.getBindingResult().getFieldErrors().forEach(
                err ->
                        errors.append(err.getField()).append(": ")
                                .append(err.getDefaultMessage()).append(";")
        );

        ErrorResponse err = new ErrorResponse(errors.toString(), request.getRequestURI(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles general exception
     *
     * @param ex
     * @param request
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> internalServerError(Exception ex, HttpServletRequest request) {
        log.error("error:::" + ex.getMessage());
        log.error("Type of errror" + ex.getClass().toString());
        if ("Access Denied".equalsIgnoreCase(ex.getMessage())) {
            ErrorResponse err = new ErrorResponse("Access Denied", request.getRequestURI(), HttpStatus.FORBIDDEN.value());
            return new ResponseEntity<>(err, HttpStatus.FORBIDDEN);
        }

        ErrorResponse err = new ErrorResponse("Something went wrong", request.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
