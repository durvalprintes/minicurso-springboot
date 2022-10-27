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
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.ufopa.spring.mapper.ClienteMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
  private Boolean enviaEmail = false;
  private Double rendaMedia = 0D;
  @CreatedDate
  private LocalDateTime createdDate;
  @LastModifiedDate
  private LocalDateTime modifiedDate;

  public ClienteMapper toDto(Class<? extends ClienteMapper> dtoClass) {
    try {
      ClienteMapper dto = dtoClass.getDeclaredConstructor().newInstance();
      BeanUtils.copyProperties(this, dto);
      return dto;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

}
