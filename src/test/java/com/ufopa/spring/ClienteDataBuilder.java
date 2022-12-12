package com.ufopa.spring;

import java.time.LocalDate;
import java.util.UUID;

import com.ufopa.spring.model.Cliente;

public class ClienteDataBuilder {

  public static Cliente onlyWithId() {
    return Cliente.builder()
        .id(UUID.randomUUID())
        .build();
  }

  public static Cliente cliente1() {
    return Cliente.builder()
        .nome("CLIENTE TESTE B")
        .dataNascimento(LocalDate.parse("1999-09-09"))
        .email("TESTE1@TESTANDO.COM")
        .telefone("91988888888")
        .build();
  }

  public static Cliente cliente2() {
    return Cliente.builder()
        .nome("CLIENTE TESTE A")
        .dataNascimento(LocalDate.parse("2010-10-20"))
        .email("TESTE2@TESTANDO.COM")
        .telefone("91977777777")
        .build();
  }

  public static String clienteJson() {
    return "{" +
        "\"nome\":\"CLIENTE TESTE\", " +
        "\"dataNascimento\":\"2001-01-20\", " +
        "\"email\":\"TESTE@TESTANDO.COM\", " +
        "\"telefone\":\"91999999999\" " +
        "}";
  }

  public static String clienteJsonComSomenteDataInvalida() {
    return "{" +
        "\"nome\":\"CLIENTE TESTE\", " +
        "\"dataNascimento\":\"1889-01-20\", " +
        "\"email\":\"TESTE@TESTANDO.COM\", " +
        "\"telefone\":\"91888888888\" " +
        "}";
  }

  public static String clienteJsonComTodosCamposInvalidos() {
    return "{" +
        "\"nome\":\"Cliente TESTE\", " +
        "\"email\":\"teste\", " +
        "\"telefone\":\"TESTANDO\" " +
        "}";
  }

}
