package com.ufopa.spring.dto;

import com.ufopa.spring.mapper.ClienteMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResumoDto implements ClienteMapper {

  private String nome;
  private String telefone;
  private String email;

}
