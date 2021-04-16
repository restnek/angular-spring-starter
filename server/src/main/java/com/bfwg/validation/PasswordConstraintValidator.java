package com.bfwg.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordData;
import org.passay.PasswordValidator;

public class PasswordConstraintValidator implements ConstraintValidator<Password, String> {
    private final PasswordValidator validator;

    public PasswordConstraintValidator() {
        validator = new PasswordValidator(
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1));
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return password != null && validator.validate(new PasswordData(password)).isValid();
    }
}