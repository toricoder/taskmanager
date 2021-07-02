package com.tasks.taskmanager.common;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Target({FIELD})
@Constraint(validatedBy = TaskStatusValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface TaskStatusConstraint {
    Class<? extends Enum<?>> enumClass();
    String message() default "Invalid value for Status field";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
