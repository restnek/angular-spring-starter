package com.bfwg.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LatinLettersConstraintValidator
        implements ConstraintValidator<LatinLetters, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || value.chars().allMatch(Dictionaries::isLatinLetter);
    }
}
