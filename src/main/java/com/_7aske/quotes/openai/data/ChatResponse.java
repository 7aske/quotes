package com._7aske.quotes.openai.data;

import java.util.List;

public record ChatResponse(List<Choice> choices) {
    public record Choice(int index, Message message) {
    }
}