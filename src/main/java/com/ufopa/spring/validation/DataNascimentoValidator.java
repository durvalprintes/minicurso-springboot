package com.ufopa.spring.validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class DataNascimentoValidator implements ConstraintValidator<DataNascimento, String> {

  @Override
  public void initialize(DataNascimento dataNascimento) {
    dataNascimento.message();
  }

  @Override
  public boolean isValid(String dataNascimento, ConstraintValidatorContext context) {
    try {
      return dataNascimento == null || periodoValido(dataNascimento);
    } catch (Exception e) {
      return false;
    }
  }

  private boolean periodoValido(@DateTimeFormat(iso = ISO.DATE) String dataNascimento) {
    LocalDate data = LocalDate.parse(dataNascimento, DateTimeFormatter.ISO_LOCAL_DATE);
    return data.isAfter(LocalDate.parse("1899-12-31")) && data.isBefore(LocalDate.now());
  }

}
