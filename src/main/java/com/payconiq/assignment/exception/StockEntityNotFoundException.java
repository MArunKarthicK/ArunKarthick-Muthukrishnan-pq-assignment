package com.payconiq.assignment.exception;

public class StockEntityNotFoundException extends RuntimeException{
    public StockEntityNotFoundException(String message) {
        super(message);
    }
}
