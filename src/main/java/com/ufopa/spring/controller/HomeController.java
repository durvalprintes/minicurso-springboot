package com.ufopa.spring.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/home")
public class HomeController {

  @Value("${home.hello}")
  private String mensagem;

  @GetMapping(value = "/hello")
  String hello(Principal principal) {
    return "Hello, " + (Optional.ofNullable(principal).isPresent() ? principal.getName() : mensagem);
  }

}
