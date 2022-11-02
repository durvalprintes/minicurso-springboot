package com.ufopa.spring.exception;

public class ResourceNotFoundException extends Exception {

  public ResourceNotFoundException() {
    super();
  }

  public ResourceNotFoundException(String mensagem) {
    super(mensagem);
  }

}
