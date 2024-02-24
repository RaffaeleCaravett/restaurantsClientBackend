package com.example.orders.exceptions;

import lombok.Getter;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
public class UnauthorizedException extends RuntimeException{
    private List<ObjectError> errorList;
    public UnauthorizedException(String message) {
        super(message);
    }
}

