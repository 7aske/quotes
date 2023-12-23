package com._7aske.quotes.service;

import com._7aske.quotes.model.Quote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface QuoteService {
    Quote getRandom();

    Page<Quote> findAllShared(Pageable pageable);

    Page<Quote> findAllForUser(String user, Pageable pageable);

    Quote findById(String id);

    Quote findByIdOrRandom(Optional<String> id);

    Quote save(Quote quote);

    Quote update(Quote quote);

    void delete(String id);
}
