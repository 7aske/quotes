package com._7aske.quotes.htmx.security;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxRequestHeader;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponseHeader;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

import static com._7aske.quotes.security.SecurityConfig.LOGIN_PAGE;

public class HtmxAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (request.getHeader(HtmxRequestHeader.HX_REQUEST.getValue()) != null) {
            response.setHeader(HtmxResponseHeader.HX_REDIRECT.getValue(), LOGIN_PAGE);
        } else {
            response.sendRedirect(LOGIN_PAGE);
        }
    }
}
