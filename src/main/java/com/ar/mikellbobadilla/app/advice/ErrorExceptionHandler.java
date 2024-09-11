package com.ar.mikellbobadilla.app.advice;

import com.ar.mikellbobadilla.app.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ErrorExceptionHandler {

    /* Global Handle Exceptions */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    ErrorResponse errorValidHandler(MethodArgumentNotValidException exc) {
        Map<String, String> errors = new HashMap<>();

        exc.getFieldErrors().forEach(fieldError -> {
            String field = fieldError.getField();
            String errorMess = fieldError.getDefaultMessage();
            errors.put(field, errorMess);
        });
        return buildResponse(BAD_REQUEST, errors);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    ErrorResponse notFoundHandler(ResourceNotFoundException exc) {
        return buildResponse(NOT_FOUND, exc.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    ErrorResponse runtimeExceptionHandler(RuntimeException exc) {
        log.error(exc.getMessage(), exc.getCause());
        return buildResponse(INTERNAL_SERVER_ERROR, "Error server");
    }

    private ErrorResponse buildResponse(HttpStatus status, Object error) {
        return new ErrorResponse(status.value(), error);
    }
}
