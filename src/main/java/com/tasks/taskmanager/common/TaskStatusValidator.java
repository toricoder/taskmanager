package com.tasks.taskmanager.common;

import com.tasks.taskmanager.rest.Status;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

@Component
public class TaskStatusValidator implements ConstraintValidator<TaskStatusConstraint, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || Arrays.stream(Status.values()).anyMatch(e -> e.name().equals(value));
    }
}
