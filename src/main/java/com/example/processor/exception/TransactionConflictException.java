package com.example.processor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class TransactionConflictException extends RuntimeException {
    public TransactionConflictException(String message) {
        super(message);
    }
}

