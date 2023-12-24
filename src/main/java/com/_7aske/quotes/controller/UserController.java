package com._7aske.quotes.controller;

import com._7aske.quotes.mapper.QuoteMapper;
import com._7aske.quotes.model.Quote;
import com._7aske.quotes.service.QuoteService;
import com._7aske.quotes.service.UserQuoteService;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponseHeader;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserQuoteService userQuoteService;
    private final QuoteService quoteService;
    private final QuoteMapper quoteMapper;

    private static final int PAGE_SIZE = 5;

    @GetMapping("/quotes/is-favourite")
    public @ResponseBody ResponseEntity<Void> isFavourite(@AuthenticationPrincipal AuthenticatedPrincipal principal,
                                                          @RequestParam String quoteId) {
        if (userQuoteService.isFavourite(quoteId, principal.getName())) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
        }

        return ResponseEntity.noContent().build();
    }

    @HxRequest
    @PostMapping(value = "/quotes/add-favourite", consumes = "application/x-www-form-urlencoded")
    public String addFavourite(@AuthenticationPrincipal AuthenticatedPrincipal principal,
                               @RequestParam String quoteId,
                               Model model,
                               HttpServletResponse response) {
        userQuoteService.addFavourite(quoteId, principal.getName());
        model.addAttribute("quote", quoteMapper.toDto(quoteService.findById(quoteId)));
        response.setHeader(HtmxResponseHeader.HX_PUSH_URL.getValue(), "/?quoteId=" + quoteId);
        return "fragments/quote-card :: quote_card";
    }

    @HxRequest
    @PostMapping("/quotes/remove-favourite")
    public String removeFavourite(@AuthenticationPrincipal AuthenticatedPrincipal principal,
                                  @RequestParam String quoteId,
                                  @PageableDefault(PAGE_SIZE) Pageable page,
                                  Model model) {
        userQuoteService.removeFavourite(quoteId, principal.getName());
        model.addAttribute("quotes", userQuoteService.listFavourites(principal, page).map(quoteMapper::toDto));
        model.addAttribute("favourites", true);
        return "user/list-quotes :: quotes-container";
    }

    @HxRequest
    @PostMapping("/quotes/remove-quote")
    public String removeQuote(@AuthenticationPrincipal AuthenticatedPrincipal principal,
                              @RequestParam String quoteId,
                              @PageableDefault(PAGE_SIZE) Pageable page,
                              Model model) {
        userQuoteService.removeQuote(quoteId, principal.getName());
        model.addAttribute("quotes", userQuoteService.listQuotes(principal, page).map(quoteMapper::toDto));
        return "user/list-quotes :: quotes-container";
    }

    @GetMapping("/quotes/add-quote")
    public String addQuote(Model model) {
        model.addAttribute("quote", new Quote());
        return "user/add-quote";
    }

    @PostMapping("/quotes/add-quote")
    public String saveQuote(@ModelAttribute @Valid Quote quote,
                            @AuthenticationPrincipal OAuth2User user,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/add-quote";
        }

        quote.setAddedBy(user.getAttribute("name"));

        userQuoteService.save(quote);

        return "redirect:/user/quotes/list-quotes";
    }


    @GetMapping("/quotes/list-quotes")
    public String listQuotes(@AuthenticationPrincipal AuthenticatedPrincipal principal,
                             @PageableDefault(PAGE_SIZE) Pageable page,
                             Model model) {
        model.addAttribute("quotes", userQuoteService.listQuotes(principal, page).map(quoteMapper::toDto));
        return "user/list-quotes";
    }

    @GetMapping("/quotes/favourites")
    public String favourites(@AuthenticationPrincipal AuthenticatedPrincipal principal,
                             @PageableDefault(PAGE_SIZE) Pageable page,
                             Model model) {
        model.addAttribute("quotes", userQuoteService.listFavourites(principal, page).map(quoteMapper::toDto));
        model.addAttribute("favourites", true);
        return "user/list-quotes";
    }
}
