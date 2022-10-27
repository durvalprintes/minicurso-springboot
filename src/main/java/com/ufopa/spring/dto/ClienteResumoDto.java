package com.ufopa.spring.dto;

import java.util.UUID;

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

  private UUID id;
  private String nome;
  private String email;

}
