package com._7aske.quotes.htmx.security;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxRequestHeader;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

import static com._7aske.quotes.security.SecurityConfig.LOGIN_PAGE;

public class HtmxAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        if (request.getHeader(HtmxRequestHeader.HX_REQUEST.getValue()) != null) {
            throw accessDeniedException;
        }

        response.sendRedirect(LOGIN_PAGE);
    }
}
