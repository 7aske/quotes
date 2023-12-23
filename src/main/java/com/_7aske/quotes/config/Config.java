package com._7aske.quotes.config;

import jakarta.validation.ValidatorFactory;
import org.hibernate.cfg.ValidationSettings;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.Optional;

@Configuration
public class Config {
    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Principal::getName)
                .or(() -> Optional.of("system"));
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(ValidatorFactory validatorFactory) {
        return properties -> properties.put(ValidationSettings.JAKARTA_VALIDATION_FACTORY, validatorFactory);
    }

}
