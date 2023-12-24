package com._7aske.quotes;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@SpringBootTest
class ApplicationTests {

    @Configuration
    public static class TestConfig {
        @Bean
        ClientRegistrationRepository clientRegistrationRepository() {
            return registrationId ->
                    ClientRegistration.withRegistrationId(registrationId)
                    .build();
        }
    }

    @Test
    void contextLoads() {
    }
}
