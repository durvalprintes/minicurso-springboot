package com.ufopa.spring.dto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ClienteDetalheDto {

  private UUID id;
  private String nome;
  private String dataNascimento;
  private String telefone;
  private String email;
  private Boolean enviaEmail;
  private Double rendaMedia;

  @EqualsAndHashCode.Exclude
  private LocalDateTime ultimaModificacao;

  @EqualsAndHashCode.Include
  private LocalDateTime truncatedtUltimaModificacao() {
    return ultimaModificacao.truncatedTo(ChronoUnit.MILLIS);
  }

}
