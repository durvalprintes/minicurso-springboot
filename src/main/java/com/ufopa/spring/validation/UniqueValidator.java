package com.ufopa.spring.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.ufopa.spring.service.ClienteService;

public class UniqueValidator implements ConstraintValidator<Unique, String> {

  @Autowired
  private ClienteService service;

  @Override
  public void initialize(Unique unique) {
    unique.message();
  }

  @Override
  public boolean isValid(String campo, ConstraintValidatorContext context) {
    return campo == null || service.isUnique(campo);

  }

}
