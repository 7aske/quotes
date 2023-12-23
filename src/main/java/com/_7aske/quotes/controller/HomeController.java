package com._7aske.quotes.controller;

import com._7aske.quotes.mapper.QuoteMapper;
import com._7aske.quotes.model.Quote;
import com._7aske.quotes.service.QuoteService;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class HomeController {
    private final QuoteService quoteService;
    private final QuoteMapper quoteMapper;

    private static final String QUOTE_ATTR = "quote";

    @GetMapping("/")
    public String index(@RequestParam Optional<String> quoteId, Model model) {
        model.addAttribute(QUOTE_ATTR, quoteMapper.toDto(quoteService.findByIdOrRandom(quoteId)));
        return "index";
    }

    @HxRequest
    @GetMapping("/random")
    public String random(Model model) {
        model.addAttribute(QUOTE_ATTR, quoteMapper.toDto(quoteService.getRandom()));
        return "fragments/quote-card :: quote_card";
    }

    @PostMapping
    @RequestMapping("/save")
    public String saveQuote(@Valid @ModelAttribute Quote quote) {
        quoteService.save(quote);
        return "redirect:/";
    }
}