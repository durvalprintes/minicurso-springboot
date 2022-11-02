package com.ufopa.spring.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.ufopa.spring.dto.ClienteResumoDto;
import com.ufopa.spring.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, UUID>, JpaSpecificationExecutor<Cliente> {

  public Page<Cliente> findByNomeContainsIgnoreCaseOrderByNome(String nome, Pageable pageable);

  @Query(value = "SELECT new com.ufopa.spring.dto.ClienteResumoDto(c.id, c.nome, c.email) FROM Cliente c WHERE UPPER(c.email) LIKE UPPER(CONCAT('%', ?1, '%'))")
  public Page<ClienteResumoDto> findClienteResumoByEmail(String email, Pageable pageable);

  public boolean existsByEmailIgnoreCase(String email);

  public boolean existsByTelefone(String telefone);

}