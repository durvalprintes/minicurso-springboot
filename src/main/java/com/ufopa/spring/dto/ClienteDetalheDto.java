package com.ufopa.spring.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDetalheDto {

  private UUID id;
  private String nome;
  private String dataNascimento;
  private String telefone;
  private String email;
  private Boolean enviaEmail;
  private Double rendaMedia;
  private LocalDateTime ultimaModificacao;

}
