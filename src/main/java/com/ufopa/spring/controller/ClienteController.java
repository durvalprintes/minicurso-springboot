package com.ufopa.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufopa.spring.dto.ClienteDetalheDto;
import com.ufopa.spring.mapper.ClienteMapper;
import com.ufopa.spring.service.ClienteService;

@RestController
@RequestMapping(path = "/clientes")
public class ClienteController {

  @Autowired
  ClienteService clienteService;

  @GetMapping
  ResponseEntity<List<ClienteMapper>> listarClientes() {
    return clienteService.getClientes();
  }

  @GetMapping(value = "/{id}")
  ResponseEntity<ClienteMapper> listarCliente(@PathVariable("id") Long id) {
    return clienteService.getCliente(id);
  }

  @PostMapping
  ResponseEntity<Object> inserirCliente(@RequestBody ClienteDetalheDto cliente) {
    return clienteService.saveCliente(cliente);
  }

  @PutMapping(value = "/{id}")
  ResponseEntity<Object> alterarCliente(@PathVariable("id") Long id, @RequestBody ClienteDetalheDto cliente) {
    return clienteService.updateCliente(id, cliente);
  }

  @DeleteMapping(value = "/{id}")
  ResponseEntity<Object> excluirCliente(@PathVariable("id") Long id) {
    return clienteService.deleteCliente(id);
  }

}
