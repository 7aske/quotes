package com._7aske.quotes.validation;

import jakarta.validation.Constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target(TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = QuoteValidator.class)
public @interface QuoteValid {
    String message() default "Quote is not valid";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
