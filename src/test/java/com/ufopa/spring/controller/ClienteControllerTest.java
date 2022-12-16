package com.ufopa.spring.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThrows;
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
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufopa.spring.ClienteDataBuilder;
import com.ufopa.spring.config.security.SecurityCoreConfig;
import com.ufopa.spring.dto.ClienteInputDto;
import com.ufopa.spring.dto.ClienteResumoDto;
import com.ufopa.spring.dto.LoginDto;
import com.ufopa.spring.exception.ErrorCampoDto;
import com.ufopa.spring.service.ClienteService;
import com.ufopa.spring.service.LoginService;

import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("squid:S5778")
@ActiveProfiles("test")
@WebMvcTest(controllers = { ClienteController.class })
@Import(value = { SecurityCoreConfig.class, LoginController.class, LoginService.class })
public class ClienteControllerTest {

  @Autowired
  private MockMvc request;

  @MockBean
  private ClienteService service;

  @Test
  void deveriaRetornarError401ComTokenNaoEncontrado() throws Exception {
    Exception exception = assertThrows(AuthenticationCredentialsNotFoundException.class,
        () -> request.perform(get("/clientes")).andExpect(status().isUnauthorized()));
    assertEquals("Token nao encontrado", exception.getMessage());
  }

  @Test
  void deveriaRetornarError401ComTokenIncorreto() throws Exception {
    Exception exception = assertThrows(AuthenticationCredentialsNotFoundException.class,
        () -> executaGetClientes("token").andExpect(status().isUnauthorized()));
    assertEquals("Token invalido", exception.getMessage());
  }

  @Test
  @WithUserDetails(value = "user")
  void deveriaRetornarListaDeResumoClientes() throws Exception {
    ClienteResumoDto resumo = new ClienteResumoDto(UUID.randomUUID(), "CLIENTE TESTE", "TESTE@TESTANDO.COM");
    when(service.getClientes()).thenReturn(List.of(resumo));

    executaGetClientes(null)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", is(notNullValue())))
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].id", containsString(resumo.getId().toString())));
  }

  @Test
  @WithUserDetails(value = "user")
  void deveriaRetornarSemConteudo() throws Exception {
    when(service.getClientes()).thenReturn(new ArrayList<>());
    executaGetClientes(null).andExpect(status().isNoContent());
  }

  @Test
  @WithUserDetails(value = "user")
  void deveriaRetornarError403ComUsuarioSemPermissao() throws Exception {
    executaPostClientes().andExpect(status().isForbidden());
  }

  @Test
  @WithUserDetails(value = "admin1")
  void deveriaRetornarLocationHeaderComIddoClienteInserido() throws Exception {
    UUID id = UUID.randomUUID();
    when(service.isUnique(anyString())).thenReturn(true);
    when(service.saveCliente(any(ClienteInputDto.class))).thenReturn(id);

    String location = executaPostClientes()
        .andExpect(status().isCreated())
        .andReturn().getResponse().getHeader("location");
    assertNotNull(location);
    assertTrue(location.contains(id.toString()));
  }

  @Test
  void deveriaRetornarArgumentNotValidExceptionComCamposNaoUnicos() throws Exception {
    when(service.isUnique(anyString())).thenReturn(false);

    List<ErrorCampoDto> lista = executaPostClientesComAssertivas(ClienteDataBuilder.clienteJson());
    assertEquals(2, lista.size());
  }

  @Test
  void deveriaRetornarArgumentNotValidExceptionComDataInvalida() throws Exception {
    when(service.isUnique(anyString())).thenReturn(true);

    List<ErrorCampoDto> lista = executaPostClientesComAssertivas(ClienteDataBuilder.clienteJsonComSomenteDataInvalida());
    assertEquals(1, lista.size());
    assertEquals("dataNascimento", lista.get(0).getCampo());
  }

  @Test
  void deveriaRetornarArgumentNotValidExceptionComInsertDeCamposInvalidos() throws Exception {
    when(service.isUnique(anyString())).thenReturn(true);

    List<ErrorCampoDto> lista = executaPostClientesComAssertivas(ClienteDataBuilder.clienteJsonComTodosCamposInvalidos());
    assertEquals(5, lista.size());
  }

  private ResultActions executaGetClientes(String token) throws Exception {
    return request.perform(get("/clientes").header("Authorization", getAuthorization(token)));
  }

  private String getAuthorization(String token) throws Exception {
    if (StringUtils.hasText(token)) {
      return "Bearer " + token;
    }
    LoginDto login = executaLogin(null);
    return login.getTokenType() + login.getAccessToken();
  }

  private ResultActions executaPostClientes() throws Exception {
    LoginDto login = executaLogin(null);
    return request
        .perform(
            post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ClienteDataBuilder.clienteJson())
                .header("Authorization", login.getTokenType() + login.getAccessToken()));
  }

  private List<ErrorCampoDto> executaPostClientesComAssertivas(String json) throws Exception {
    LoginDto login = executaLogin(new Credenciais("admin2", "password"));
    String response = request
        .perform(
            post("/clientes")
                .header("Authorization", login.getTokenType() + login.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
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

  private LoginDto executaLogin(Credenciais credenciais) throws Exception {
    String login = request
        .perform(credenciais != null ? postRequest(credenciais) : postRequest())
        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
    var mapper = new ObjectMapper();
    return mapper.readValue(login, LoginDto.class);
  }

  private RequestBuilder postRequest() {
    return post("/login");
  }

  private RequestBuilder postRequest(Credenciais credenciais) {
    return post("/login").with(httpBasic(credenciais.getUsername(), credenciais.getPassword()));
  }

}

@Getter
@AllArgsConstructor
class Credenciais {
  private String username;
  private String password;
}
