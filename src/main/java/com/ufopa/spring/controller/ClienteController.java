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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ufopa.spring.dto.ClienteDetalheDto;
import com.ufopa.spring.dto.ClienteInputDto;
import com.ufopa.spring.dto.ClienteResumoDto;
import com.ufopa.spring.exception.ResourceNotFoundException;
import com.ufopa.spring.exception.SearchException;
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
    List<ClienteResumoDto> clientes = clienteService.getClientes();
    return clientes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(clientes);
  }

  @GetMapping(value = "/{id}")
  ResponseEntity<ClienteDetalheDto> listarCliente(@PathVariable("id") UUID id) throws ResourceNotFoundException {
    return ResponseEntity.ok(clienteService.getCliente(id));
  }

  @PostMapping
  ResponseEntity<Object> inserirCliente(@RequestBody @Validated(OnInsert.class) ClienteInputDto cliente) {
    return ResponseEntity.created(ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(clienteService.saveCliente(cliente))
        .toUri()).build();
  }

  @PutMapping(value = "/{id}")
  ResponseEntity<Object> alterarCliente(@PathVariable("id") UUID id,
      @RequestBody @Validated(OnUpdate.class) ClienteInputDto cliente) throws ResourceNotFoundException {
    clienteService.updateCliente(id, cliente);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping(value = "/{id}")
  ResponseEntity<Object> excluirCliente(@PathVariable("id") UUID id) throws ResourceNotFoundException {
    clienteService.deleteCliente(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping(value = "/busca")
  ResponseEntity<Page<ClienteResumoDto>> buscarClientes(@RequestParam(required = false, defaultValue = "") String nome,
      @RequestParam(required = false, defaultValue = "") String email, Pageable pageable) throws SearchException {
    return ResponseEntity.ok(clienteService.getClientes(nome, email, pageable));
  }

}
