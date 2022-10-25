package com.ufopa.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufopa.spring.dto.ClienteDto;
import com.ufopa.spring.dto.MapperCliente;
import com.ufopa.spring.service.ClienteService;

@RestController
@RequestMapping(path = "/clientes")
public class ClienteController {

  @Autowired
  ClienteService clienteService;

  @GetMapping
  ResponseEntity<List<MapperCliente>> listarClientes() {
    return clienteService.getClientes();
  }

  @GetMapping(value = "/{id}")
  ResponseEntity<MapperCliente> listarCliente(@PathVariable("id") Long id) {
    return clienteService.getCliente(id);
  }

  @PostMapping
  ResponseEntity<Object> inserirCliente(@RequestBody ClienteDto cliente) {
    return clienteService.saveCliente(cliente);
  }

}
