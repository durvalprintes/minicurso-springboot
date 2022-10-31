package com.ufopa.spring.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ufopa.spring.dto.ClienteDetalheDto;
import com.ufopa.spring.dto.ClienteResumoDto;
import com.ufopa.spring.exception.ResourceNotFoundException;
import com.ufopa.spring.mapper.ClienteMapper;
import com.ufopa.spring.model.Cliente;
import com.ufopa.spring.repository.ClienteRepository;

@Service
public class ClienteService {

  @Autowired
  ClienteRepository clienteRepository;

  public ResponseEntity<List<ClienteMapper>> getClientes() {
    List<Cliente> clientes = clienteRepository.findAll();
    return clientes.isEmpty() ? ResponseEntity.noContent().build()
        : ResponseEntity
            .ok(clientes.stream().map(cliente -> cliente.toDto(ClienteResumoDto.class)).collect(Collectors.toList()));
  }

  public ResponseEntity<ClienteMapper> getCliente(UUID id) throws ResourceNotFoundException {
    Cliente cliente = clienteRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    return ResponseEntity.ok(cliente.toDto(ClienteDetalheDto.class));
  }

  public ResponseEntity<Object> saveCliente(ClienteDetalheDto dto) {
    return ResponseEntity.created(ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(clienteRepository.save(dto.toModel()).getId())
        .toUri()).build();
  }

  public ResponseEntity<Object> updateCliente(UUID id, ClienteDetalheDto dto) throws ResourceNotFoundException {
    Cliente cliente = clienteRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    clienteRepository.save(dto.toModel(cliente));
    return ResponseEntity.noContent().build();
  }

  public ResponseEntity<Object> deleteCliente(UUID id) throws ResourceNotFoundException {
    Cliente cliente = clienteRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    clienteRepository.delete(cliente);
    return ResponseEntity.noContent().build();
  }

  public ResponseEntity<Page<ClienteMapper>> findByNome(String nome, Pageable pageable) {
    Page<Cliente> clientes = clienteRepository.findByNomeContainsIgnoreCaseOrderByNome(nome, pageable);
    return ResponseEntity
        .ok(new PageImpl<>(
            clientes.stream().map(cliente -> cliente.toDto(ClienteResumoDto.class)).collect(Collectors.toList()),
            pageable, clientes.getTotalPages()));
  }

  public ResponseEntity<Page<ClienteResumoDto>> findByEmail(String email, Pageable pageable) {
    return ResponseEntity.ok(clienteRepository.findClienteResumoByEmail(email, pageable));
  }

}
