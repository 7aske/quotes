package com._7aske.quotes.data;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuoteDto {
    private String id;
    private String text;
    private String author;
    private String authorImage;
    private boolean favourite = false;
    private long favouriteCount;
    private String addedBy;
    private LocalDateTime createdDate;
}
