package com.bfwg.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LatinAlphanumericConstraintValidator
        implements ConstraintValidator<LatinAlphanumeric, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || value.chars().allMatch(Dictionaries::isLatinAlphanumeric);
    }
}
