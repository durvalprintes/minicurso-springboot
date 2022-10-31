package com.ufopa.spring.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.ufopa.spring.validation.DataNascimento;
import com.ufopa.spring.validation.OnInsert;
import com.ufopa.spring.validation.OnUpdate;
import com.ufopa.spring.validation.Unique;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDetalheDto {

  @NotNull(groups = OnInsert.class)
  @Pattern(regexp = "[A-Z ]+", message = "somente letras maiúsculas e espaços", groups = { OnInsert.class,
      OnUpdate.class })
  private String nome;
  @NotNull(groups = OnInsert.class)
  @DataNascimento(groups = { OnInsert.class, OnUpdate.class })
  private String dataNascimento;
  @NotNull(groups = OnInsert.class)
  @Pattern(regexp = "\\d{11}", message = "somente números e com 11 dígitos", groups = { OnInsert.class,
      OnUpdate.class })
  @Unique(groups = { OnInsert.class, OnUpdate.class })
  private String telefone;
  @NotNull(groups = OnInsert.class)
  @Email(groups = { OnInsert.class, OnUpdate.class })
  @Pattern(regexp = "[^a-z]+", message = "não deve conter letras minúsculas", groups = { OnInsert.class,
      OnUpdate.class })
  @Unique(groups = { OnInsert.class, OnUpdate.class })
  private String email;
  private Boolean enviaEmail;
  private Double rendaMedia;

}
