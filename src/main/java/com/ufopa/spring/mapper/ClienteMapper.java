package com.ufopa.spring.mapper;

import java.beans.FeatureDescriptor;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.ufopa.spring.model.Cliente;

public interface ClienteMapper {

  default Cliente toModel() {
    Cliente cliente = new Cliente();
    BeanUtils.copyProperties(this, cliente);
    return cliente;
  }

  default Cliente toModel(Cliente cliente) {
    BeanUtils.copyProperties(this, cliente, getNullPropertyNames(this));
    return cliente;
  }

  static String[] getNullPropertyNames(Object source) {
    final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
    return Stream.of(wrappedSource.getPropertyDescriptors())
        .map(FeatureDescriptor::getName)
        .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
        .toArray(String[]::new);
  }

}
