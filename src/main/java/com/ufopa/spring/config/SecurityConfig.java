package com.ufopa.spring.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.stream.Stream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  enum PERMISSAO {
    LEITURA, ESCRITA;
  }

  private static final String SENHA_PADRAO = "{noop}password";

  private static final String[] SWAGGER = {
      "/swagger-resources/**",
      "/swagger-ui/**",
      "/v2/api-docs",
      "/webjars/**"
  };

  @Bean
  public UserDetailsService userDetailsManager() {
    var user = User.builder()
        .username("user")
        .password(SENHA_PADRAO)
        .roles(PERMISSAO.LEITURA.name())
        .build();

    var admin1 = User.builder()
        .username("admin1")
        .password(SENHA_PADRAO)
        .roles(getPermissoes())
        .build();

    var admin2 = User.builder()
        .username("admin2")
        .password(SENHA_PADRAO)
        .roles(getPermissoes())
        .build();

    return new InMemoryUserDetailsManager(user, admin1, admin2);
  }

  @Bean
  public SecurityFilterChain configure(HttpSecurity http) throws Exception {
    return http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeRequests(auth -> {
          auth.antMatchers(SWAGGER).permitAll();
          auth.antMatchers("/home/hello").permitAll();
          auth.antMatchers("/manage/**").hasRole(PERMISSAO.ESCRITA.name());
          auth.antMatchers(HttpMethod.GET).hasRole(PERMISSAO.LEITURA.name());
          auth.anyRequest().hasRole(PERMISSAO.ESCRITA.name());
        })
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .httpBasic(withDefaults())
        .build();
  }

  private String[] getPermissoes() {
    return Stream.of(PERMISSAO.values()).map(Enum::name).toArray(String[]::new);
  }

}
