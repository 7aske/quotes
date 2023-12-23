package com._7aske.quotes.service.impl;

import com._7aske.quotes.model.Quote;
import com._7aske.quotes.repository.QuotesRepository;
import com._7aske.quotes.service.QuoteService;
import com._7aske.quotes.service.QuoteValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuoteServiceImpl implements QuoteService {
    private final QuotesRepository quotesRepository;
    private final QuoteValidationService quoteValidationService;

    @Override
    public Quote getRandom() {
        return quotesRepository.getRandom()
                .orElseThrow();
    }

    @Override
    public Page<Quote> findAllShared(Pageable pageable) {
        return quotesRepository.findAll(
                        (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("shared"), true),
                        pageable
                );
    }

    @Override
    public Page<Quote> findAllForUser(String user, Pageable pageable) {
        return quotesRepository.findAll(
                        (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("createdBy"), user),
                        pageable
                );
    }

    @Override
    public Quote findById(String id) {
        return quotesRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Quote not found"));
    }

    @Override
    public Quote findByIdOrRandom(Optional<String> id) {
        return id.map(this::findById)
                .orElseGet(this::getRandom);
    }

    @Override
    public Quote save(Quote quote) {
        return quotesRepository.save(quote);
    }

    @Override
    public Quote update(Quote quote) {
        return quotesRepository.save(quote);
    }

    @Override
    public void delete(String id) {
        quotesRepository.deleteById(id);
    }
}
