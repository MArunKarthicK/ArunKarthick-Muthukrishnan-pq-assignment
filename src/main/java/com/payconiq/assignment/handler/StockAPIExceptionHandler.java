package com.payconiq.assignment.handler;

import com.payconiq.assignment.exception.InvalidJwtAuthenticationException;
import com.payconiq.assignment.exception.StockApiError;
import com.payconiq.assignment.exception.StockEntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class StockAPIExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(StockEntityNotFoundException.class)
    public ResponseEntity<?> handleStockEntityNotFoundException(StockEntityNotFoundException ex) {
        String error = "NOT FOUND";
        log.debug("handleStockEntityNotFoundException:{}",ex.getMessage());
        return buildResponseEntity(new StockApiError(HttpStatus.NOT_FOUND, error, ex.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";
        log.debug("handleHttpMessageNotReadable:{}",ex.getMessage());
        return buildResponseEntity(new StockApiError(HttpStatus.BAD_REQUEST, error, ex.getMessage()));
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<?> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();
        log.debug("MethodArgumentTypeMismatchException:{}",ex.getMessage());
        return buildResponseEntity(new StockApiError(HttpStatus.BAD_REQUEST, error, ex.getMessage()));
    }

    @ExceptionHandler(InvalidJwtAuthenticationException.class)
    public ResponseEntity<?> handleInvalidJwtAuthenticationException(InvalidJwtAuthenticationException ex) {
        String error = "Invalid JWT Token";
        log.debug("handleInvalidJwtAuthenticationException:{}",ex.getMessage());
        return buildResponseEntity(new StockApiError(HttpStatus.BAD_REQUEST, error, ex.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex) {
        String error = "Invalid Credentials";
        log.debug("handleBadCredentialsException:{}",ex.getMessage());
        return buildResponseEntity(new StockApiError(HttpStatus.FORBIDDEN, error, ex.getMessage()));
    }

    private ResponseEntity<Object> buildResponseEntity(StockApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

}
