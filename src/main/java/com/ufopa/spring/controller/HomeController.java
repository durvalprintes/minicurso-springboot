package com.ufopa.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/home")
public class HomeController {

  @GetMapping(value = "/hello")
  String hello() {
    return "Hello World!";
  }

}
