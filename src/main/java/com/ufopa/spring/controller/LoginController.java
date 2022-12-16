package com.ufopa.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufopa.spring.dto.LoginDto;
import com.ufopa.spring.service.LoginService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/login")
public class LoginController {

  @Autowired
  private LoginService service;

  @PostMapping
  public ResponseEntity<LoginDto> login(Authentication authentication) {
    log.debug("Token requisitado pelo usuario: '{}'", authentication.getName());
    var login = LoginDto.builder().accessToken(service.generateToken(authentication)).build();
    return ResponseEntity.ok(login);
  }

}