package com.ufopa.spring.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ufopa.spring.dto.ClienteDto;
import com.ufopa.spring.dto.MapperCliente;
import com.ufopa.spring.dto.ResumoClienteDto;
import com.ufopa.spring.model.Cliente;
import com.ufopa.spring.repository.ClienteRepository;

@Service
public class ClienteService {

  @Autowired
  ClienteRepository clienteRepository;

  public ResponseEntity<List<MapperCliente>> getClientes() {
    List<Cliente> clientes = clienteRepository.findAll();
    return clientes.isEmpty() ? ResponseEntity.noContent().build()
        : ResponseEntity
            .ok(clientes.stream().map(cliente -> cliente.toDto(new ResumoClienteDto())).collect(Collectors.toList()));
  }

  public ResponseEntity<MapperCliente> getCliente(Long id) {
    Optional<Cliente> cliente = clienteRepository.findById(id);
    return cliente.isPresent() ? ResponseEntity.ok(cliente.get().toDto(new ClienteDto()))
        : ResponseEntity.notFound().build();
  }

  public ResponseEntity<Object> saveCliente(ClienteDto dto) {
    return ResponseEntity.created(ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(clienteRepository.save(dto.toModel()).getId())
        .toUri()).build();
  }

}
