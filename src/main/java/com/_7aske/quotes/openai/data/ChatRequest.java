package com._7aske.quotes.openai.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatRequest {
    private final String model;
    private List<Message> messages;
    private Integer n;
    private Double temperature;

    public ChatRequest(String model, String prompt) {
        this.model = model;

        this.messages = new ArrayList<>();
        this.messages.add(new Message("system", prompt));
    }

    public ChatRequest prompt(String prompt) {
        this.messages.add(new Message("user", prompt));
        return this;
    }

    // getters and setters
}