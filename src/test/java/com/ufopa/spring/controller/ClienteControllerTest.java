package com.ufopa.spring.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufopa.spring.config.SecurityConfig;
import com.ufopa.spring.dto.ClienteInputDto;
import com.ufopa.spring.dto.ClienteResumoDto;
import com.ufopa.spring.exception.ErrorCampoDto;
import com.ufopa.spring.service.ClienteService;

@ActiveProfiles("test")
@WebMvcTest(controllers = ClienteController.class)
@Import(value = SecurityConfig.class)
public class ClienteControllerTest {

  @Autowired
  private MockMvc request;

  @MockBean
  private ClienteService service;

  @Test
  void deveriaRetornarError401SemUsuarioAutenticado() throws Exception {
    request.perform(get("/clientes")).andExpect(status().isUnauthorized());
  }

  @Test
  @WithAnonymousUser
  void deveriaRetornarError401ComUsuarioIncorreto() throws Exception {
    request
        .perform(get("/clientes"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithUserDetails(value = "user")
  void deveriaRetornarListaDeResumoClientes() throws Exception {
    ClienteResumoDto resumo = new ClienteResumoDto(UUID.randomUUID(), "CLIENTE TESTE", "TESTE@TESTANDO.COM");
    when(service.getClientes()).thenReturn(List.of(resumo));

    request
        .perform(get("/clientes"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", is(notNullValue())))
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].id", containsString(resumo.getId().toString())));
  }

  @Test
  @WithUserDetails(value = "user")
  void deveriaRetornarSemConteudo() throws Exception {
    when(service.getClientes()).thenReturn(new ArrayList<>());
    
    request
    .perform(get("/clientes"))
    .andExpect(status().isNoContent());
  }

  @Test
  @WithUserDetails(value = "user")
  void deveriaRetornarError403ComUsuarioSemPermissao() throws Exception {
    request
        .perform(post("/clientes"))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithUserDetails(value = "admin1")
  void deveriaRetornarLocationHeaderComIddoClienteInserido() throws Exception {
    UUID id = UUID.randomUUID();
    when(service.isUnique(anyString())).thenReturn(true);
    when(service.saveCliente(any(ClienteInputDto.class))).thenReturn(id);

    String location = request
        .perform(
            post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clienteJson()))
        .andExpect(status().isCreated())
        .andReturn().getResponse().getHeader("location");
    assertNotNull(location);
    assertTrue(location.contains(id.toString()));
  }

  @Test
  void deveriaRetornarArgumentNotValidExceptionComCamposNaoUnicos() throws Exception {
    when(service.isUnique(anyString())).thenReturn(false);

    List<ErrorCampoDto> lista = executaPostComClienteJson(clienteJson());
    assertEquals(2, lista.size());
  }

  @Test
  void deveriaRetornarArgumentNotValidExceptionComDataInvalida() throws Exception {
    when(service.isUnique(anyString())).thenReturn(true);

    
    List<ErrorCampoDto> lista = executaPostComClienteJson(clienteJsonComSomenteDataInvalida());
    assertEquals(1, lista.size());
    assertEquals("dataNascimento", lista.get(0).getCampo());
  }

  @Test
  void deveriaRetornarArgumentNotValidExceptionComInsertDeCamposInvalidos() throws Exception {
    when(service.isUnique(anyString())).thenReturn(true);

    List<ErrorCampoDto> lista = executaPostComClienteJson(clienteJsonComTodosCamposInvalidos());
    assertEquals(5, lista.size());
  }

  private List<ErrorCampoDto> executaPostComClienteJson(String json) throws Exception {
    String response = request
        .perform(
            post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .with(httpBasic("admin2", "password")))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status", is(400)))
        .andExpect(jsonPath("$.descricao", is("Entrada de dados inválida")))
        .andExpect(jsonPath("$.listaErros", is(notNullValue())))
        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    var mapper = new ObjectMapper().readTree(response);
    return StreamSupport.stream(mapper.get("listaErros").spliterator(), false)
        .map(erro -> new ErrorCampoDto(erro.get("campo").asText(), erro.get("mensagem").asText()))
        .collect(Collectors.toList());
  }

  private String clienteJson() {
    return "{" +
        "\"nome\":\"CLIENTE TESTE\", " +
        "\"dataNascimento\":\"2001-01-20\", " +
        "\"email\":\"TESTE@TESTANDO.COM\", " +
        "\"telefone\":\"91999999999\" " +
        "}";
  }

  private String clienteJsonComSomenteDataInvalida() {
    return "{" +
        "\"nome\":\"CLIENTE TESTE\", " +
        "\"dataNascimento\":\"1889-01-20\", " +
        "\"email\":\"TESTE@TESTANDO.COM\", " +
        "\"telefone\":\"91888888888\" " +
        "}";
  }

  private String clienteJsonComTodosCamposInvalidos() {
    return "{" +
        "\"nome\":\"Cliente TESTE\", " +
        "\"email\":\"teste\", " +
        "\"telefone\":\"TESTANDO\" " +
        "}";
  }

}
