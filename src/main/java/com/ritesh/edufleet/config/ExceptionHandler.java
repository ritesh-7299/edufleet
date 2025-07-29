package com.ritesh.edufleet.config;

import com.ritesh.edufleet.exception.BadRequestException;
import com.ritesh.edufleet.exception.ResourceNotFoundException;
import com.ritesh.edufleet.system.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionHandler {
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

        if ("Access Denied".equalsIgnoreCase(ex.getMessage())) {
            ErrorResponse err = new ErrorResponse("Access Denied", request.getRequestURI(), HttpStatus.FORBIDDEN.value());
            return new ResponseEntity<>(err, HttpStatus.FORBIDDEN);
        }

        ErrorResponse err = new ErrorResponse("Something went wrong", request.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
