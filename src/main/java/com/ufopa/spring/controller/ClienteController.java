package com.ufopa.spring.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ufopa.spring.dto.ClienteDetalheDto;
import com.ufopa.spring.dto.ClienteResumoDto;
import com.ufopa.spring.exception.ResourceNotFoundException;
import com.ufopa.spring.service.ClienteService;
import com.ufopa.spring.validation.OnInsert;
import com.ufopa.spring.validation.OnUpdate;

@RestController
@RequestMapping(path = "/clientes")
public class ClienteController {

  @Autowired
  ClienteService clienteService;

  @GetMapping
  ResponseEntity<List<ClienteResumoDto>> listarClientes() {
    return clienteService.getClientes();
  }

  @GetMapping(value = "/{id}")
  ResponseEntity<ClienteDetalheDto> listarCliente(@PathVariable("id") UUID id) throws ResourceNotFoundException {
    return clienteService.getCliente(id);
  }

  @PostMapping
  ResponseEntity<Object> inserirCliente(@RequestBody @Validated(OnInsert.class) ClienteDetalheDto cliente) {
    return clienteService.saveCliente(cliente);
  }

  @PutMapping(value = "/{id}")
  ResponseEntity<Object> alterarCliente(@PathVariable("id") UUID id,
      @RequestBody @Validated(OnUpdate.class) ClienteDetalheDto cliente) throws ResourceNotFoundException {
    return clienteService.updateCliente(id, cliente);
  }

  @DeleteMapping(value = "/{id}")
  ResponseEntity<Object> excluirCliente(@PathVariable("id") UUID id) throws ResourceNotFoundException {
    return clienteService.deleteCliente(id);
  }

  @GetMapping(value = "/busca/1")
  ResponseEntity<Page<ClienteResumoDto>> buscarClientesPorNome(@RequestParam(required = true) String nome,
      Pageable pageable) {
    return clienteService.findByNome(nome, pageable);
  }

  @GetMapping(value = "/busca/2")
  ResponseEntity<Page<ClienteResumoDto>> buscarClientesPorEmail(@RequestParam(required = true) String email,
      Pageable pageable) {
    return clienteService.findByEmail(email, pageable);
  }

}
