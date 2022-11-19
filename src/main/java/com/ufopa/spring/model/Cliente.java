package com.ufopa.spring.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "clientes")
public class Cliente extends BaseEntity {

  @Column(nullable = false)
  private String nome;

  @Column(nullable = false)
  private LocalDate dataNascimento;

  @Column(length = 11, nullable = false, unique = true)
  private String telefone;

  @Column(nullable = false, unique = true)
  private String email;

  @Builder.Default
  @Column(columnDefinition = "bool not null default false")
  private Boolean enviaEmail = false;

  @Builder.Default
  @Column(columnDefinition = "float8 not null default 0")
  private Double rendaMedia = 0D;

}
