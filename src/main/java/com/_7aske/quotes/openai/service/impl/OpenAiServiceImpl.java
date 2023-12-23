package com._7aske.quotes.openai.service.impl;

import com._7aske.quotes.openai.config.OpenAiProperties;
import com._7aske.quotes.openai.data.ChatRequest;
import com._7aske.quotes.openai.data.ChatResponse;
import com._7aske.quotes.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenAiServiceImpl implements OpenAiService {
    private final OpenAiProperties properties;

    @Qualifier("openaiRestTemplate")
    private final RestTemplate restTemplate;

    @Override
    public String chat(String prompt) {
        log.info("System message: \n{}", properties.getSystemMessage());
        log.info("Prompt: \n{}", prompt);

        ChatRequest request = new ChatRequest(properties.getModel(), properties.getSystemMessage())
                .prompt(prompt);

        ChatResponse response;
        try {
            response = restTemplate.postForObject(properties.getApiUrl(), request, ChatResponse.class);
        } catch (RestClientException e) {
            log.error("Cannot validate the request", e);
            return "No response";
        }

        if (response == null || response.choices() == null || response.choices().isEmpty()) {
            log.warn("No response from OpenAI");
            return "No response";
        }

        return response.choices().get(0).message().content();
    }
}
