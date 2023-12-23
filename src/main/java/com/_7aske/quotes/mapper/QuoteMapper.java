package com._7aske.quotes.mapper;

import com._7aske.quotes.data.QuoteDto;
import com._7aske.quotes.model.Quote;
import com._7aske.quotes.service.UserQuoteService;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Mapper
@SuppressWarnings({"java:S6813", "SpringJavaAutowiredFieldsWarningInspection"})
public abstract class QuoteMapper {

    @Autowired
    private UserQuoteService userQuoteService;

    public abstract QuoteDto toDto(Quote quote);

    public abstract Quote toEntity(QuoteDto quoteDto);

    @AfterMapping
    protected void setIsFavourite(@MappingTarget QuoteDto quoteDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return;
        }

        quoteDto.setFavourite(userQuoteService.isFavourite(quoteDto.getId(), authentication.getName()));
    }
}
