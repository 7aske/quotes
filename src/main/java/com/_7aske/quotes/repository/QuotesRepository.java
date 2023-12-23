package com._7aske.quotes.repository;

import com._7aske.quotes.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface QuotesRepository extends JpaRepository<Quote, String>, JpaSpecificationExecutor<Quote> {
    @Query(value = "SELECT * FROM quote WHERE shared = 1 ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<Quote> getRandom();
}
