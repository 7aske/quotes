package com._7aske.quotes.service;

import com._7aske.quotes.model.Quote;

public interface QuoteValidationService {
    boolean isValid(Quote quote);
}
