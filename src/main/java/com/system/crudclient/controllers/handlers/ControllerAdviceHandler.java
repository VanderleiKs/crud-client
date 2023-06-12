package com.system.crudclient.controllers.handlers;

import java.time.Instant;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.system.crudclient.dtos.CustomError;
import com.system.crudclient.dtos.FieldMessage;
import com.system.crudclient.services.exceptions.NotFoundExceptionService;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerAdviceHandler {

    @ExceptionHandler(NotFoundExceptionService.class)
    public ResponseEntity<CustomError> notFound(NotFoundExceptionService e, HttpServletRequest http) {
        var status = HttpStatus.NOT_FOUND;
        var error = new CustomError(Instant.now(), status.value(), e.getMessage(), http.getRequestURI(), null);
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> argumentNotValid(MethodArgumentNotValidException e, HttpServletRequest http) {
        var status = HttpStatus.UNPROCESSABLE_ENTITY;
        var errors = new ArrayList<FieldMessage>();
        e.getBindingResult().getFieldErrors()
                .forEach(f -> errors.add(new FieldMessage(f.getField(), f.getDefaultMessage())));
        var error = new CustomError(Instant.now(), status.value(), "Campos Inválidos", http.getRequestURI(), errors);
        return ResponseEntity.status(status).body(error);
    }
}
