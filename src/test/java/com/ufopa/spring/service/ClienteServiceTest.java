package com.ufopa.spring.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.ufopa.spring.dto.ClienteDetalheDto;
import com.ufopa.spring.dto.ClienteInputDto;
import com.ufopa.spring.dto.ClienteResumoDto;
import com.ufopa.spring.exception.ResourceNotFoundException;
import com.ufopa.spring.mapper.ClienteMapper;
import com.ufopa.spring.model.Cliente;
import com.ufopa.spring.repository.ClienteRepository;

@ActiveProfiles("test")
@WebMvcTest(controllers = ClienteService.class)
public class ClienteServiceTest {

  @Autowired
  ClienteService service;

  @MockBean
  ClienteRepository repository;

  @MockBean
  ClienteMapper mapper;

  @Test
  void deveriaRetornarResumoDeTodosOsClientes() {
    Cliente cliente = clienteDeTeste();
    when(repository.findAll()).thenReturn(List.of(cliente));
    when(mapper.clienteToResumoDto(cliente)).thenReturn(ClienteResumoDto.builder().id(cliente.getId()).build());
    List<ClienteResumoDto> resumos = service.getClientes();
    assertEquals(1, resumos.size());
    assertEquals(cliente.getId(), resumos.get(0).getId());
  }

  @Test
  void deveriaRetornarDetalheDeUmCliente() throws ResourceNotFoundException {
    Cliente cliente = clienteDeTeste();
    when(repository.findById(any(UUID.class))).thenReturn(Optional.of(cliente));
    when(mapper.clienteToDetalheDto(cliente)).thenReturn(ClienteDetalheDto.builder().id(cliente.getId()).build());
    ClienteDetalheDto detalhe = service.getCliente(cliente.getId());
    assertEquals(cliente.getId(), detalhe.getId());
  }

  @Test
  void deveriaRetornarResourceNotFoundException() throws ResourceNotFoundException {
    when(repository.findById(any(UUID.class))).thenReturn(null);
    assertThrows(ResourceNotFoundException.class, () ->  service.getCliente(any(UUID.class)));
  }

  @Test
  void deveriaRetornarIdDoNovoCliente() {
    Cliente cliente = clienteDeTeste();
    when(mapper.clienteFromDto(any(ClienteInputDto.class))).thenReturn(cliente);
    when(repository.save(cliente)).thenReturn(cliente);
    assertEquals(cliente.getId(), service.saveCliente(new ClienteInputDto()));
  }

  private Cliente clienteDeTeste() {
    return Cliente.builder()
        .id(UUID.randomUUID())
        .build();
  }

}
