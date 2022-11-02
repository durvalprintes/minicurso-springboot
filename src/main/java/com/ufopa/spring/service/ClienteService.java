package com.ufopa.spring.service;

import java.util.List;
import java.util.UUID;
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
import com.ufopa.spring.exception.SearchException;
import com.ufopa.spring.mapper.ClienteMapper;
import com.ufopa.spring.model.Cliente;
import com.ufopa.spring.repository.ClienteRepository;
import com.ufopa.spring.specification.ClienteSpecs;

@Service
public class ClienteService {

  @Autowired
  ClienteRepository clienteRepository;

  public ResponseEntity<List<ClienteResumoDto>> getClientes() {
    List<Cliente> clientes = clienteRepository.findAll();
    return clientes.isEmpty() ? ResponseEntity.noContent().build()
        : ResponseEntity
            .ok(clientes.stream().map(ClienteMapper.INSTANCE::clienteToResumoDto).collect(Collectors.toList()));
  }

  public ResponseEntity<ClienteDetalheDto> getCliente(UUID id) throws ResourceNotFoundException {
    return ResponseEntity.ok(ClienteMapper.INSTANCE
        .clienteToDetalheDto(clienteRepository.findById(id).orElseThrow(ResourceNotFoundException::new)));
  }

  public ResponseEntity<Object> saveCliente(ClienteDetalheDto dto) {
    return ResponseEntity.created(ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(clienteRepository.save(ClienteMapper.INSTANCE.clienteFromDto(dto)).getId())
        .toUri()).build();
  }

  public ResponseEntity<Object> updateCliente(UUID id, ClienteDetalheDto dto) throws ResourceNotFoundException {
    clienteRepository.save(ClienteMapper.INSTANCE.clienteFromDto(dto,
        clienteRepository.findById(id).orElseThrow(ResourceNotFoundException::new)));
    return ResponseEntity.noContent().build();
  }

  public ResponseEntity<Object> deleteCliente(UUID id) throws ResourceNotFoundException {
    Cliente cliente = clienteRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    clienteRepository.delete(cliente);
    return ResponseEntity.noContent().build();
  }

  public ResponseEntity<Page<ClienteResumoDto>> getClientes(String nome, String email, Pageable pageable)
      throws SearchException {
    if (!nome.isEmpty()) {
      Page<Cliente> clientes = !email.isEmpty()
          ? clienteRepository.findAll(ClienteSpecs.getClientesByContainNomeAndEmail(nome, email), pageable)
          : clienteRepository.findByNomeContainsIgnoreCaseOrderByNome(nome, pageable);

      return ResponseEntity.ok(new PageImpl<>(
          clientes.stream().map(ClienteMapper.INSTANCE::clienteToResumoDto).collect(Collectors.toList()), pageable,
          clientes.getTotalPages()));
    }
    if (!email.isEmpty()) {
      return ResponseEntity.ok(clienteRepository.findClienteResumoByEmail(email, pageable));
    }
    throw new SearchException("Nenhum parâmetro de busca encontrado");
  }

}
