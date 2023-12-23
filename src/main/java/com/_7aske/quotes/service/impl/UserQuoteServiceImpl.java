package com._7aske.quotes.service.impl;

import com._7aske.quotes.model.FavouriteQuote;
import com._7aske.quotes.model.Quote;
import com._7aske.quotes.repository.FavouriteQuoteRepository;
import com._7aske.quotes.service.QuoteService;
import com._7aske.quotes.service.UserQuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQuoteServiceImpl implements UserQuoteService {
    private final FavouriteQuoteRepository favouriteQuoteRepository;
    private final QuoteService quoteService;

    @Override
    public boolean isFavourite(String quoteId, String user) {
        return favouriteQuoteRepository.existsById(new FavouriteQuote.FavouriteQuoteId(quoteId, user));
    }

    @Override
    public void addFavourite(String quoteId, String user) {
        if (isFavourite(quoteId, user)) {
            removeFavourite(quoteId, user);
        } else {
            favouriteQuoteRepository.save(new FavouriteQuote(new FavouriteQuote.FavouriteQuoteId(quoteId, user)));
        }
    }

    @Override
    public void removeFavourite(String quoteId, String user) {
        favouriteQuoteRepository.deleteById(new FavouriteQuote.FavouriteQuoteId(quoteId, user));
    }

    @Override
    public Page<Quote> listQuotes(AuthenticatedPrincipal principal, Pageable page) {
        return quoteService.findAllForUser(principal.getName(), page);
    }

    @Override
    public Page<Quote> listFavourites(AuthenticatedPrincipal principal, Pageable page) {
        return favouriteQuoteRepository.findAllByUser(principal.getName(), page);
    }

    @Override
    public void save(Quote quote) {
        quoteService.save(quote);
    }

    @Override
    public void removeQuote(String quoteId, String name) {
        Quote quote = quoteService.findById(quoteId);
        if (quote.getCreatedBy().equals(name)) {
            quoteService.delete(quoteId);
        }
    }
}
