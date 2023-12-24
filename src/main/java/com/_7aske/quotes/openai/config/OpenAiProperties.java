package com._7aske.quotes.openai.config;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Validated
@AllArgsConstructor
@ConfigurationProperties(prefix = "openai")
public class OpenAiProperties {
    private String apiKey;

    @NotBlank
    private String apiUrl;

    @NotBlank
    private String model;

    @NotBlank
    private String systemMessage;
}
