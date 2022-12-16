package com.ufopa.spring.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

  @Autowired
  private JwtEncoder encoder;

  public String generateToken(Authentication authentication) {
    Instant now = Instant.now();
    String scope = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(" "));
    JwtClaimsSet claims = JwtClaimsSet.builder()
        .subject(authentication.getName())
        .issuedAt(now)
        .expiresAt(now.plus(1, ChronoUnit.HOURS))
        .claim("scope", scope)
        .build();
    return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }
}
