package com.ufopa.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AuthController {

  @Autowired
  private AuthService service;

  @PostMapping("/token")
  public String token(Authentication authentication) {
    log.debug("Token requisitado pelo usuario: '{}'", authentication.getName());
    String token = service.generateToken(authentication);
    log.debug("Token criado: {}", token);
    return token;
  }

}