package com.ufopa.spring.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import com.ufopa.spring.mapper.ClienteMapper;
import com.ufopa.spring.validation.OnInsert;
import com.ufopa.spring.validation.OnUpdate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDetalheDto implements ClienteMapper {

  @NotNull(groups = OnInsert.class)
  @Pattern(regexp = "[A-Z ]+", message = "somente letras maiúsculas e espaços", groups = { OnInsert.class,
      OnUpdate.class })
  private String nome;
  @NotNull(groups = OnInsert.class)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate dataNascimento;
  @NotNull(groups = OnInsert.class)
  @Pattern(regexp = "\\d{11}", message = "somente números e com 11 dígitos", groups = { OnInsert.class,
      OnUpdate.class })
  private String telefone;
  @NotNull(groups = OnInsert.class)
  @Email(groups = { OnInsert.class, OnUpdate.class })
  @Pattern(regexp = "[^a-z]+", message = "não deve conter letras minúsculas", groups = { OnInsert.class,
      OnUpdate.class })
  private String email;
  private Boolean enviaEmail;
  private Double rendaMedia;

}
