package com.example.springbootdemo.structs.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EndDateValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEndDate
{
    String message() default "End date must be either 'Present' or in the format YYYY-MM-DD";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}