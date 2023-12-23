package com._7aske.quotes.repository;

import com._7aske.quotes.model.FavouriteQuote;
import com._7aske.quotes.model.Quote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FavouriteQuoteRepository extends JpaRepository<FavouriteQuote, FavouriteQuote.FavouriteQuoteId> {
    @Query(value = "select q from Quote q where q.id in (select fq.id.quoteId from FavouriteQuote fq where fq.id.user = ?1)")
    Page<Quote> findAllByUser(String user, Pageable page);
}
