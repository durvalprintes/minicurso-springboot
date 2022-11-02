package com.ufopa.spring.specification;

import org.springframework.data.jpa.domain.Specification;

import com.ufopa.spring.model.Cliente;

public class ClienteSpecs {

  private ClienteSpecs() {
  }

  public static Specification<Cliente> getClientesByContainNome(String nome) {
    return (root, query, builder) -> builder.like(builder.upper(root.get("nome")), "%" + nome.toUpperCase() + "%");
  }

  public static Specification<Cliente> getClientesByContainEmail(String email) {
    return (root, query, builder) -> builder.like(builder.upper(root.get("email")), "%" + email.toUpperCase() + "%");
  }

  public static Specification<Cliente> getClientesByContainNomeAndEmail(String nome, String email) {
    return Specification.where(getClientesByContainNome(nome)).and(getClientesByContainEmail(email));
  }
}
