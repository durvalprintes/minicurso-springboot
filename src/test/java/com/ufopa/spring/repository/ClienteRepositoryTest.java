package com.ufopa.spring.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import com.ufopa.spring.config.JpaConfig;
import com.ufopa.spring.dto.ClienteResumoDto;
import com.ufopa.spring.model.Cliente;

@ActiveProfiles("test")
@DataJpaTest
@Import(JpaConfig.class)
public class ClienteRepositoryTest {

  @Autowired
  private ClienteRepository repository;

  @Test
  void deveriaRetornarResumoDeClientesContendoNome() {
    UUID id1 = repository.save(clienteDeTeste()).getId();
    UUID id2 = repository.save(clienteDeTeste2()).getId();
    Page<Cliente> clientes = repository.findByNomeContainsIgnoreCaseOrderByNome("TESTE".toLowerCase(), null);
    assertEquals(2L, clientes.get().count());
    assertEquals(id2, clientes.get().findFirst().get().getId());
    assertEquals(id1, clientes.get().skip(1).findFirst().get().getId());
  }

  @Test
  void deveriaRetornarResumoDeClientesContendoEmail() {
    Cliente cliente = clienteDeTeste();
    UUID id = repository.save(cliente).getId();
    Page<ClienteResumoDto> clientes = repository.findClienteResumoByEmail(
        "@", null);
    assertNotNull(clientes.stream().filter(resumo -> resumo.getId().equals(id)).findAny().get());
  }

  @Test
  void deveriaRetornarVerdadeiroOuFalsoSeExistirAlgumClienteComEmailIgual() {
    Cliente cliente = repository.save(clienteDeTeste());
    assertTrue(repository.existsByEmailIgnoreCase(cliente.getEmail()));
    repository.delete(cliente);
    assertFalse(repository.existsByEmailIgnoreCase(cliente.getEmail()));
  }

  @Test
  void deveriaRetornarVerdadeiroOuFalsoSeExisterAlgumClienteComTelefoneIgual() {
    Cliente cliente = repository.save(clienteDeTeste());
    assertTrue(repository.existsByTelefone(cliente.getTelefone()));
    repository.delete(cliente);
    assertFalse(repository.existsByTelefone(cliente.getTelefone()));
  }

  private Cliente clienteDeTeste() {
    return Cliente.builder()
        .nome("CLIENTE TESTE B")
        .dataNascimento(LocalDate.parse("1999-09-09"))
        .email("TESTE1@TESTANDO.COM")
        .telefone("91988888888")
        .build();
  }

  private Cliente clienteDeTeste2() {
    return Cliente.builder()
        .nome("CLIENTE TESTE A")
        .dataNascimento(LocalDate.parse("2010-10-20"))
        .email("TESTE2@TESTANDO.COM")
        .telefone("91977777777")
        .build();
  }

}
