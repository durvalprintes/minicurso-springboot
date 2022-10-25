package com.ufopa.spring.dto;

import org.springframework.beans.BeanUtils;

import com.ufopa.spring.model.Cliente;

public interface MapperCliente {

  default Cliente toModel() {
    Cliente cliente = new Cliente();
    BeanUtils.copyProperties(this, cliente);
    return cliente;
  }

}
