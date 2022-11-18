package com.ufopa.spring.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditor")
public class JpaConfig {

  @Bean
  public AuditorAware<String> auditor() {
    return new Auditor();
  }

}

class Auditor implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return Optional.of(authentication != null && authentication.isAuthenticated() ? authentication.getName() : "0");
  }

}
