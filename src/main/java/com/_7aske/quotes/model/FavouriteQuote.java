package com._7aske.quotes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@Table(name = "favourite_quote")
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor @AllArgsConstructor
public class FavouriteQuote extends Auditable {
    @EmbeddedId
    private FavouriteQuoteId id;

    @Data
    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FavouriteQuoteId implements Serializable {
        @Column(name = "quote_id")
        private String quoteId;

        @Column(name = "`user`")
        private String user;
    }
}
