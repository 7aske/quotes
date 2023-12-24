package com._7aske.quotes.security;

import com._7aske.quotes.htmx.security.HtmxAccessDeniedHandler;
import com._7aske.quotes.htmx.security.HtmxAuthenticationEntryPoint;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxRequestHeader;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
@ConditionalOnBean(ClientRegistrationRepository.class)
public class SecurityConfig {
    private final ClientRegistrationRepository clientRegistrationRepository;

    public static final String LOGIN_PAGE = "/login";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity sec) throws Exception {
        return sec
                .authorizeHttpRequests(http ->
                        http.requestMatchers("/", "/random").permitAll()
                                .requestMatchers("/logout").permitAll()
                                .requestMatchers("/favicon.ico").permitAll()
                                .requestMatchers("/static/**").permitAll()
                                .anyRequest().authenticated()
                )
                .cors(withDefaults())
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(ignoreHtmxRequests()))
                .oauth2Client(withDefaults())
                .oauth2Login(login -> login.
                        loginPage(LOGIN_PAGE).permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/").permitAll()
                        .logoutSuccessHandler(logoutSuccessHandler()))
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(new HtmxAuthenticationEntryPoint())
                        .accessDeniedHandler(new HtmxAccessDeniedHandler())
                )
                .build();
    }

    private LogoutSuccessHandler logoutSuccessHandler() {
        return new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
    }

    private RequestMatcher ignoreHtmxRequests() {
        return new RequestHeaderRequestMatcher(HtmxRequestHeader.HX_REQUEST.getValue());
    }
}
