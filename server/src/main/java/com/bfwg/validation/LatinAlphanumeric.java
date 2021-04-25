package com.bfwg.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LatinAlphanumericConstraintValidator.class)
public @interface LatinAlphanumeric {
    String message() default "Must contain only latin alphanumeric";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
