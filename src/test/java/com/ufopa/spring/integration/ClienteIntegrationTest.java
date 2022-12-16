package com.ufopa.spring.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufopa.spring.BaseTestContainer;
import com.ufopa.spring.dto.ClienteDetalheDto;
import com.ufopa.spring.dto.ClienteResumoDto;
import com.ufopa.spring.dto.LoginDto;
import com.ufopa.spring.mapper.ClienteMapper;
import com.ufopa.spring.repository.ClienteRepository;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClienteIntegrationTest extends BaseTestContainer {

  private static final UUID ID_CLIENTE1 = UUID.fromString("bd911320-480f-4bd9-a128-c4b2b570cec0");
  private static final UUID ID_CLIENTE2 = UUID.fromString("e66b91cc-d81d-49c8-81cf-c48b7b965cfb");

  @Autowired
  private TestRestTemplate api = new TestRestTemplate();

  @Autowired
  ClienteRepository repository;

  @Autowired
  private ClienteMapper mapper;

  @Test
  void deveriaRetornarResumodeClientes() throws Exception {
    ResponseEntity<List<ClienteResumoDto>> response = api
        .exchange("/clientes", HttpMethod.GET, requestWithAuthorization(),
            new ParameterizedTypeReference<List<ClienteResumoDto>>() {
            });
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());

    var data = Optional.ofNullable(response.getBody()).get();

    assertEquals(2, data.size());
    assertNotNull(data.stream().filter(resumo -> resumo.getId().equals(ID_CLIENTE1)).findAny());
    assertNotNull(data.stream().filter(resumo -> resumo.getId().equals(ID_CLIENTE2)).findAny());
  }

  @Test
  void deveriaRetornarDetalheDeCliente() throws Exception {
    ResponseEntity<ClienteDetalheDto> response = api
        .exchange("/clientes/ " + ID_CLIENTE1.toString(), HttpMethod.GET, requestWithAuthorization(),
            ClienteDetalheDto.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());

    var detalheCliente = Optional.of(response.getBody()).get();
    var cliente = repository.findById(ID_CLIENTE1).get();

    assertEquals(mapper.clienteToDetalheDto(cliente).hashCode(), detalheCliente.hashCode());
  }

  private HttpEntity<?> requestWithAuthorization() throws Exception {
    LoginDto login = executaLogin();
    var headers = new HttpHeaders();
    headers.set("Authorization", login.getTokenType() + login.getAccessToken());
    return new HttpEntity<>(headers);
  }

  private LoginDto executaLogin() throws Exception {
    String login = api
        .withBasicAuth("user", "password")
        .postForEntity("/login", null, String.class).getBody();
    var mapper = new ObjectMapper();
    return mapper.readValue(login, LoginDto.class);

  }

}
