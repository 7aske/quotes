package com._7aske.quotes.openai.config;

import com._7aske.quotes.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class OpenAiConfig {
    private final OpenAiProperties openAiProperties;

    @Bean
    @ConditionalOnMissingBean(OpenAiService.class)
    public OpenAiService noOpOpenAiService() {
        return prompt -> "Quote valid";
    }

    @Bean
    public RestTemplate openaiRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer " + openAiProperties.getApiKey());
            return execution.execute(request, body);
        });
        return restTemplate;
    }

}
