package com.ufopa.spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ufopa.spring.model.Cliente;
import com.ufopa.spring.repository.ClienteRepository;

@Service
public class ClienteService {

  @Autowired
  ClienteRepository clienteRepository;

  public ResponseEntity<List<Cliente>> getClientes() {
    List<Cliente> clientes = clienteRepository.findAll();
    return clientes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(clientes);
  }

  public ResponseEntity<Cliente> getCliente(Long id) {
    Optional<Cliente> cliente = clienteRepository.findById(id);
    return cliente.isPresent() ? ResponseEntity.ok(cliente.get()) : ResponseEntity.notFound().build();
  }

}
