package com.ufopa.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.ufopa.spring.dto.ClienteDetalheDto;
import com.ufopa.spring.dto.ClienteResumoDto;
import com.ufopa.spring.mapper.ClienteMapper;
import com.ufopa.spring.repository.ClienteRepository;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClienteIntegrationTest {

  @Autowired
  private TestRestTemplate api = new TestRestTemplate();

  @Autowired
  private ClienteRepository repository;

  @Autowired
  private ClienteMapper mapper;

  @Test
  void deveriaRetornarResumodeClientes() {
    var cliente1 = repository.save(ClienteDataBuilder.cliente1());
    var cliente2 = repository.save(ClienteDataBuilder.cliente2());
    ResponseEntity<List<ClienteResumoDto>> response = api
        .withBasicAuth("user", "password")
        .exchange("/clientes",
            HttpMethod.GET, null,
            new ParameterizedTypeReference<List<ClienteResumoDto>>() {
            });
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());

    var data = Optional.ofNullable(response.getBody()).get();

    assertEquals(2, data.size());
    assertNotNull(data.stream().filter(resumo -> resumo.getId().equals(cliente1.getId())).findAny());
    assertNotNull(data.stream().filter(resumo -> resumo.getId().equals(cliente2.getId())).findAny());
  }

  @Test
  void deveriaRetornarDetalheDeCliente() {
    var cliente1 = repository.save(ClienteDataBuilder.cliente1());
    ResponseEntity<ClienteDetalheDto> response = api
        .withBasicAuth("user", "password")
        .getForEntity("/clientes/" + cliente1.getId(), ClienteDetalheDto.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(mapper.clienteToDetalheDto(cliente1).hashCode(), Optional.of(response.getBody()).get().hashCode());
  }

}
