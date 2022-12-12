package com.ufopa.spring.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@ComponentScan
@EnableWebSecurity
public class SecurityCoreConfig {

  enum PERMISSAO {
    LEITURA, ESCRITA;
  }

  private static final String[] SWAGGER = {
      "/swagger-resources/**",
      "/swagger-ui/**",
      "/v2/api-docs",
      "/webjars/**"
  };

  @Autowired
  private RsaKeyPropertiesConfig chaves;

  @Bean
  public SecurityFilterChain configure(HttpSecurity http, AuthRequestFilter requestFilter) throws Exception {
    return http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeRequests(auth -> {
          auth.antMatchers(SWAGGER).permitAll();
          auth.antMatchers("/home/hello").permitAll();
          auth.antMatchers("/token").authenticated();
          auth.antMatchers("/manage/**").hasAuthority(this.getScopeAuthority(PERMISSAO.ESCRITA.name()));
          auth.antMatchers(HttpMethod.GET).hasAuthority(this.getScopeAuthority(PERMISSAO.LEITURA.name()));
          auth.anyRequest().hasAuthority(this.getScopeAuthority(PERMISSAO.ESCRITA.name()));
        })
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
        .addFilterAfter(requestFilter, UsernamePasswordAuthenticationFilter.class)
        .httpBasic(Customizer.withDefaults())
        .build();
  }

  @Bean
  JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withPublicKey(chaves.getPublicKey()).build();
  }

  @Bean
  JwtEncoder jwtEncoder() {
    var jwk = new RSAKey.Builder(chaves.getPublicKey()).privateKey(chaves.getPrivateKey()).build();
    JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
    return new NimbusJwtEncoder(jwks);
  }

  private String getScopeAuthority(String authority) {
    return "SCOPE_" + authority;
  }

}
