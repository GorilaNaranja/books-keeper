package com.feliopolis.bookskeeper.controllers;

import com.feliopolis.bookskeeper.utils.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ValidationException;

@ControllerAdvice
public class ExceptionHandlerController {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(javax.validation.ValidationException.class)
    ErrorMessage exceptionHandler(ValidationException e) {
        System.out.println("EXCEPTION HANDLER CONTROLLER");

        return new ErrorMessage("400", e.getMessage());
    }
}
