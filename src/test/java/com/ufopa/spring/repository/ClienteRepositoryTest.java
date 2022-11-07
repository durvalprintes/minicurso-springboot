package com.ufopa.spring.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import com.ufopa.spring.config.JpaConfig;
import com.ufopa.spring.dto.ClienteResumoDto;
import com.ufopa.spring.model.Cliente;

@ActiveProfiles("test")
@DataJpaTest(showSql = false)
@Import(JpaConfig.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ClienteRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  ClienteRepository repository;

  @Test
  void deveriaRetornarResumoDeClientesContendoNome() {
    UUID id1 = UUID.fromString(entityManager.persistAndGetId(clienteDeTeste()).toString());
    UUID id2 = UUID.fromString(entityManager.persistAndGetId(clienteDeTeste2()).toString());
    Page<Cliente> clientes = repository.findByNomeContainsIgnoreCaseOrderByNome("TESTE".toLowerCase(), null);
    assertEquals(2L, clientes.get().count());
    assertEquals(id2, clientes.get().findFirst().get().getId());
    assertEquals(id1, clientes.get().skip(1).findFirst().get().getId());
  }

  @Test
  void deveriaRetornarResumoDeClientesContendoEmail() {
    Cliente cliente = clienteDeTeste();
    UUID id = UUID.fromString(entityManager.persistAndGetId(cliente).toString());
    Page<ClienteResumoDto> clientes = repository.findClienteResumoByEmail(
        "@", null);
    assertNotNull(clientes.stream().filter(resumo -> resumo.getId().equals(id)).findAny().get());
  }

  @Test
  void deveriaRetornarVerdadeiroOuFalsoSeExistirAlgumClienteComEmailIgual() {
    Cliente cliente = entityManager.persist(clienteDeTeste());
    assertTrue(repository.existsByEmailIgnoreCase(cliente.getEmail()));
    entityManager.remove(cliente);
    assertFalse(repository.existsByEmailIgnoreCase(cliente.getEmail()));
  }

  @Test
  void deveriaRetornarVerdadeiroOuFalsoSeExisterAlgumClienteComTelefoneIgual() {
    Cliente cliente = entityManager.persist(clienteDeTeste());
    assertTrue(repository.existsByTelefone(cliente.getTelefone()));
    entityManager.remove(cliente);
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
