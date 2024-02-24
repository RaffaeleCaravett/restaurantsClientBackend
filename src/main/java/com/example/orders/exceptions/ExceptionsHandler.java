package com.example.orders.exceptions;

import com.example.orders.payloads.errors.ErrorsDTO;
import com.example.orders.payloads.errors.ErrorsWithListDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class ExceptionsHandler {

@ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsWithListDTO handleBadRequest(BadRequestException e){
    if(e.getErrorList()!= null){
        List<String> errorsList = e.getErrorList().stream().map(objectError -> objectError.getDefaultMessage()).toList();
        return  new ErrorsWithListDTO(e.getMessage(), new Date(),errorsList);
    }else {
        return  new ErrorsWithListDTO(e.getMessage(),new Date(),new ArrayList<>());
    }
}
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 401
    public ErrorsDTO handleUnauthorized(UnauthorizedException e) {
        return new ErrorsDTO(e.getMessage(), new Date());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN) // 403
    public ErrorsDTO handleAccessDenied(AccessDeniedException e) {
        return new ErrorsDTO(e.getMessage(), new Date());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)  // 404
    public ErrorsDTO handleNotFound(NotFoundException e) {
        return new ErrorsDTO(e.getMessage(), new Date());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  // 500
    public ErrorsDTO handleGeneric(Exception e) {
        e.printStackTrace();
        return new ErrorsDTO("Problema lato server...giuro che lo risolveremo presto", new Date());
    }
}
