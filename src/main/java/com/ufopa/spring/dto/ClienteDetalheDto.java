package com.ufopa.spring.dto;

import java.time.LocalDate;

import com.ufopa.spring.mapper.ClienteMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDetalheDto implements ClienteMapper {

  private String nome;
  private LocalDate dataNascimento;
  private String telefone;
  private String email;
  private Boolean enviaEmail;
  private Double rendaMedia;

}
