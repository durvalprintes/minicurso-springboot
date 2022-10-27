package com.ufopa.spring.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorCampoDto {

  private String campo;
  private String mensagem;

}
