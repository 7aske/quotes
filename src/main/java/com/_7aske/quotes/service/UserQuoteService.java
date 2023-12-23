package com._7aske.quotes.service;

import com._7aske.quotes.model.Quote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.AuthenticatedPrincipal;

public interface UserQuoteService {
    boolean isFavourite(String quoteId, String user);

    void addFavourite(String quoteId, String user);

    void removeFavourite(String quoteId, String user);

    Page<Quote> listQuotes(AuthenticatedPrincipal principal, Pageable page);

    Page<Quote> listFavourites(AuthenticatedPrincipal principal, Pageable page);

    void save(Quote quote);

    void removeQuote(String quoteId, String name);
}
