package com.ufopa.spring.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

import com.ufopa.spring.dto.MapperCliente;

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
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String nome;
  private LocalDate dataNascimento;
  private String telefone;
  private String email;
  private Boolean enviaEmail;
  private Double rendaMedia;

  public MapperCliente toDto(MapperCliente dto) {
    BeanUtils.copyProperties(this, dto);
    return dto;
  }

}
