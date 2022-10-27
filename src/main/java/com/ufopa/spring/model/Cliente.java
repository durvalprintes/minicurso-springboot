package com.ufopa.spring.model;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;

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
