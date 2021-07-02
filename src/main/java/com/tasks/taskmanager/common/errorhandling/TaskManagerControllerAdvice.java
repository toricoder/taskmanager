package com.tasks.taskmanager.common.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@ControllerAdvice
public class TaskManagerControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorDefinition handleConstraintViolation(ConstraintViolationException e) {

        return new ErrorDefinition(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Received invalid request. For more see details.",
                e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList())
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorDefinition handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {

        return new ErrorDefinition(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Received invalid request. For more see details.",
                Arrays.asList(e.getMessage())
        );
    }

    @ResponseStatus
    @ExceptionHandler(TaskNotFoundException.class)
    public ErrorDefinition handleTaskNotFoundException(TaskNotFoundException e) {
        return new ErrorDefinition(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase(), "Not allowed to do the operation with the request body/parameter.", Collections.emptyList());

    }
}
