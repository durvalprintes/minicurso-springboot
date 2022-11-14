package com.ufopa.spring.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Slf4j
@RestController
@RequestMapping(path = "/home")
public class HomeController {

  @Value("${home.hello}")
  private String mensagem;

  @GetMapping(value = "/hello")
  String hello(Principal principal) {
    log.info("Call hello endpoint");
    Optional.ofNullable(principal).ifPresent(usuario -> log.debug("Usuario: " + usuario.toString()));
    return "Hello, " + (Optional.ofNullable(principal).isPresent() ? principal.getName() : mensagem);
  }

}
