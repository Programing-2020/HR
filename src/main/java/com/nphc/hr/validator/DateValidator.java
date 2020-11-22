package com.nphc.hr.validator;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateValidator implements ConstraintValidator<DateValue, String> {

    List<String> authors = Arrays.asList("Santideva", "Marie Kondo", "Martin Fowler", "mkyong");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
    	System.out.println("Purushotham "+value);
        return authors.contains(value);

    }
}