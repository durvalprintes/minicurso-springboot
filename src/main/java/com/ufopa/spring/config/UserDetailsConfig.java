package com.ufopa.spring.config;

import java.util.stream.Stream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.ufopa.spring.config.SecurityConfig.PERMISSAO;

@Configuration
public class UserDetailsConfig {

  private static final String SENHA_PADRAO = "{noop}password";

  @Bean
  public UserDetailsService userDetailsManager() {
    var user = User.builder()
        .username("user")
        .password(SENHA_PADRAO)
        .authorities(PERMISSAO.LEITURA.name())
        .build();

    var admin1 = User.builder()
        .username("admin1")
        .password(SENHA_PADRAO)
        .authorities(this.getPermissoes())
        .build();

    var admin2 = User.builder()
        .username("admin2")
        .password(SENHA_PADRAO)
        .authorities(this.getPermissoes())
        .build();

    return new InMemoryUserDetailsManager(user, admin1, admin2);
  }

  private String[] getPermissoes() {
    return Stream.of(PERMISSAO.values()).map(Enum::name).toArray(String[]::new);
  }

}
