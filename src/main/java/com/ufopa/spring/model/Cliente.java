package com.ufopa.spring.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "clientes")
@EntityListeners(AuditingEntityListener.class)
public class Cliente {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  private UUID id;

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

  @Column(nullable = false, updatable = false)
  @CreatedBy
  private String inseriu;

  @Column(nullable = false)
  @LastModifiedBy
  private String alterou;

  @Column(nullable = false, updatable = false)
  @CreatedDate
  private LocalDateTime dataInsercao;

  @Column(nullable = false)
  @LastModifiedDate
  private LocalDateTime dataAlteracao;

}
