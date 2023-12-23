package com._7aske.quotes.service.impl;

import com._7aske.quotes.model.Quote;
import com._7aske.quotes.openai.service.OpenAiService;
import com._7aske.quotes.service.QuoteValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenAiQuoteValidationService implements QuoteValidationService {
    private final OpenAiService openAiService;

    @Override
    public boolean isValid(Quote quote) {
        String request = """
                Quote: %s
                Author: %s
                """
                .stripLeading()
                .formatted(quote.getText(), quote.getAuthor());
        String response = openAiService.chat(request);

        log.info("OpenAI response: {}", response);

        return response.equals("Valid quote");
    }
}
