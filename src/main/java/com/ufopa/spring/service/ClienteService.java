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

  public List<ClienteResumoDto> getClientes() {
    return clienteRepository.findAll().stream()
        .map(ClienteMapper.INSTANCE::clienteToResumoDto)
        .collect(Collectors.toList());
  }

  public ClienteDetalheDto getCliente(UUID id) throws ResourceNotFoundException {
    return ClienteMapper.INSTANCE
        .clienteToDetalheDto(clienteRepository.findById(id).orElseThrow(ResourceNotFoundException::new));
  }

  public UUID saveCliente(ClienteDetalheDto dto) {
    return clienteRepository.save(ClienteMapper.INSTANCE.clienteFromDto(dto)).getId();
  }

  public void updateCliente(UUID id, ClienteDetalheDto dto) throws ResourceNotFoundException {
    clienteRepository.save(ClienteMapper.INSTANCE.clienteFromDto(dto,
        clienteRepository.findById(id).orElseThrow(ResourceNotFoundException::new)));
  }

  public void deleteCliente(UUID id) throws ResourceNotFoundException {
    clienteRepository.delete(clienteRepository.findById(id).orElseThrow(ResourceNotFoundException::new));
  }

  public Page<ClienteResumoDto> getClientes(String nome, String email, Pageable pageable)
      throws SearchException {
    if (!nome.isEmpty()) {
      Page<Cliente> clientes = !email.isEmpty()
          ? clienteRepository.findAll(ClienteSpecs.getClientesByContainNomeAndEmail(nome, email), pageable)
          : clienteRepository.findByNomeContainsIgnoreCaseOrderByNome(nome, pageable);

      return new PageImpl<>(
          clientes.stream().map(ClienteMapper.INSTANCE::clienteToResumoDto).collect(Collectors.toList()), pageable,
          clientes.getTotalPages());
    }
    if (!email.isEmpty()) {
      return clienteRepository.findClienteResumoByEmail(email, pageable);
    }
    throw new SearchException("Nenhum parâmetro de busca encontrado");
  }

}
