package com.ufopa.spring;

import java.time.LocalDate;

import com.ufopa.spring.model.Cliente;

public class ClienteDataBuilder {

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

}
