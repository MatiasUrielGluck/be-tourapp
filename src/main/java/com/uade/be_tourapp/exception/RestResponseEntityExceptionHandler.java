package com.uade.be_tourapp.exception;

import com.uade.be_tourapp.dto.ErrorResponseDTO;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = {GenericException.class})
    protected ResponseEntity<Object> handleGenericException(
            RuntimeException ex, WebRequest request) {
        ErrorResponseDTO bodyOfResponse = ErrorResponseDTO.builder()
                .error(ex.getMessage())
                .build();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value
            = {JwtFilterException.class, ExpiredJwtException.class})
    protected ResponseEntity<Object> handleSignatureException(
            RuntimeException ex, WebRequest request) {
        ErrorResponseDTO bodyOfResponse = ErrorResponseDTO.builder()
                .error("Invalid token.")
                .build();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(value
            = {UserAlreadyExistsException.class})
    protected ResponseEntity<Object> handleUserAlreadyExists(
            RuntimeException ex, WebRequest request) {
        ErrorResponseDTO bodyOfResponse = ErrorResponseDTO.builder()
                .error("User already exists.")
                .build();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value
            = {BadRequestException.class})
    protected ResponseEntity<Object> handleBadRequest(
            RuntimeException ex, WebRequest request) {
        ErrorResponseDTO bodyOfResponse = ErrorResponseDTO.builder()
                .error(ex.getMessage())
                .build();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /* TODO: Add this handlers
    @ExceptionHandler(value
            = {EntityAlreadyExistsException.class})
    protected ResponseEntity<Object> handleEntityAlreadyExists(
            RuntimeException ex, WebRequest request) {
        ErrorResponseDTO bodyOfResponse = ErrorResponseDTO.builder()
                .error(ex.getMessage())
                .build();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value
            = {EntityNotFoundException.class})
    protected ResponseEntity<Object> handleEntityNotFound(
            RuntimeException ex, WebRequest request) {
        ErrorResponseDTO bodyOfResponse = ErrorResponseDTO.builder()
                .error(ex.getMessage())
                .build();
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
     */
}