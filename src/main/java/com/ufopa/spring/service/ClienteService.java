package com.ufopa.spring.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ufopa.spring.dto.ClienteDetalheDto;
import com.ufopa.spring.dto.ClienteInputDto;
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
  ClienteRepository repository;

  @Autowired
  ClienteMapper mapper;

  public List<ClienteResumoDto> getClientes() {
    return repository.findAll().stream()
        .map(mapper::clienteToResumoDto)
        .collect(Collectors.toList());
  }

  public ClienteDetalheDto getCliente(UUID id) throws ResourceNotFoundException {
    return mapper.clienteToDetalheDto(repository.findById(id).orElseThrow(ResourceNotFoundException::new));
  }

  public UUID saveCliente(ClienteInputDto dto) {
    return repository.save(mapper.clienteFromDto(dto)).getId();
  }

  public void updateCliente(UUID id, ClienteInputDto dto) throws ResourceNotFoundException {
    repository
        .save(mapper.clienteFromDto(dto, repository.findById(id).orElseThrow(ResourceNotFoundException::new)));
  }

  public void deleteCliente(UUID id) throws ResourceNotFoundException {
    repository.delete(repository.findById(id).orElseThrow(ResourceNotFoundException::new));
  }

  public Page<ClienteResumoDto> getClientes(String nome, String email, Pageable pageable)
      throws SearchException {
    if (!nome.isEmpty()) {
      Page<Cliente> clientes = !email.isEmpty()
          ? repository.findAll(ClienteSpecs.getClientesByContainNomeAndEmail(nome, email), pageable)
          : repository.findByNomeContainsIgnoreCaseOrderByNome(nome, pageable);

      return new PageImpl<>(
          clientes.stream().map(mapper::clienteToResumoDto).collect(Collectors.toList()), pageable,
          clientes.getTotalPages());
    }
    if (!email.isEmpty()) {
      return repository.findClienteResumoByEmail(email, pageable);
    }
    throw new SearchException("Nenhum parâmetro de busca encontrado");
  }

  public boolean isUnique(String campo) {
    return (campo.matches("\\d+")
        ? !repository.existsByTelefone(campo)
        : !repository.existsByEmailIgnoreCase(campo));
  }

}
