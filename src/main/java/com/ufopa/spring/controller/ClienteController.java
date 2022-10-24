package com.ufopa.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufopa.spring.model.Cliente;
import com.ufopa.spring.service.ClienteService;

@RestController
@RequestMapping(path = "/clientes")
public class ClienteController {

  @Autowired
  ClienteService clienteService;

  @GetMapping
  ResponseEntity<List<Cliente>> listarClientes() {
    return clienteService.getClientes();
  }

  @GetMapping(value = "/{id}")
  ResponseEntity<Cliente> listarCliente(@PathVariable("id") Long id) {
    return clienteService.getCliente(id);
  }

}
