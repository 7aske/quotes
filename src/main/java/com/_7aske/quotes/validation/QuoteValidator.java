package com._7aske.quotes.validation;

import com._7aske.quotes.model.Quote;
import com._7aske.quotes.service.QuoteValidationService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuoteValidator implements ConstraintValidator<QuoteValid, Quote> {
    @Autowired
    private QuoteValidationService quoteValidationService;

    @Override
    public void initialize(QuoteValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Quote value, ConstraintValidatorContext context) {
        return quoteValidationService.isValid(value);
    }
}
