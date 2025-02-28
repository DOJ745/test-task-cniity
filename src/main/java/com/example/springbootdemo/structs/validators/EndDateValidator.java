package com.example.springbootdemo.structs.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class EndDateValidator implements ConstraintValidator<ValidEndDate, String>
{

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context)
    {
        if (value == null || value.isEmpty())
        {
            return false;
        }

        if ("Present".equalsIgnoreCase(value))
        {
            return true;
        }

        try
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(value, formatter);

            return true;
        }
        catch (DateTimeParseException e)
        {
            return false;
        }
    }
}
