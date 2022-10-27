package com.ufopa.spring.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import com.ufopa.spring.mapper.ClienteMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDetalheDto implements ClienteMapper {

  @NotNull
  @Pattern(regexp = "[A-Z ]+")
  private String nome;
  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate dataNascimento;
  @NotNull
  @Pattern(regexp = "\\d{11}")
  private String telefone;
  @NotNull
  @Email
  @Pattern(regexp = "[^a-z]+")
  private String email;
  private Boolean enviaEmail;
  private Double rendaMedia;

}
