package com.ufopa.spring.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ufopa.spring.dto.ClienteDetalheDto;
import com.ufopa.spring.dto.ClienteResumoDto;
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

  public ResponseEntity<ClienteMapper> getCliente(Long id) {
    Optional<Cliente> cliente = clienteRepository.findById(id);
    return cliente.isPresent() ? ResponseEntity.ok(cliente.get().toDto(ClienteDetalheDto.class))
        : ResponseEntity.notFound().build();
  }

  public ResponseEntity<Object> saveCliente(ClienteDetalheDto dto) {
    return ResponseEntity.created(ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(clienteRepository.save(dto.toModel()).getId())
        .toUri()).build();
  }

  public ResponseEntity<Object> updateCliente(Long id, ClienteDetalheDto dto) {
    AtomicBoolean sucesso = new AtomicBoolean(true);
    clienteRepository.findById(id).ifPresentOrElse(cliente -> clienteRepository.save(dto.toModel(cliente)),
        () -> sucesso.set(false));
    return sucesso.get() ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }

  public ResponseEntity<Object> deleteCliente(Long id) {
    AtomicBoolean sucesso = new AtomicBoolean(true);
    clienteRepository.findById(id).ifPresentOrElse(cliente -> clienteRepository.delete(cliente),
        () -> sucesso.set(false));
    return sucesso.get() ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }

}
