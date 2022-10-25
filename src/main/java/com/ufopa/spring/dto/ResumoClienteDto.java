package com.ufopa.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResumoClienteDto implements MapperCliente {

  private String nome;
  private String telefone;
  private String email;

}
