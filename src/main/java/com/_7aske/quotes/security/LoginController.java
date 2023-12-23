package com._7aske.quotes.security;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ResolvableType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final Map<String, String> oauth2AuthenticationUrls = new HashMap<>();

    private static final String AUTHORIZATION_REQUEST_BASE_URI = "oauth2/authorization";

    @GetMapping
    public String login(Model model) {

        updateRegistrations();

        model.addAttribute("urls", oauth2AuthenticationUrls);

        return "login";
    }

    @SuppressWarnings("unchecked")
    private void updateRegistrations() {
        Iterable<ClientRegistration> clientRegistrations = new ArrayList<>();

        ResolvableType type = ResolvableType
                .forInstance(clientRegistrationRepository)
                .as(Iterable.class);

        if (type != ResolvableType.NONE && ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }


        clientRegistrations.forEach(registration ->
                oauth2AuthenticationUrls.put(registration.getClientName(), getAuthUrl(registration)));
    }

    private String getAuthUrl(ClientRegistration registration) {
        return AUTHORIZATION_REQUEST_BASE_URI + "/" + registration.getRegistrationId();
    }
}
