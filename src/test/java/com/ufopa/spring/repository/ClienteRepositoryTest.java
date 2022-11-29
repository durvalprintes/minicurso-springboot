package com.ufopa.spring.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import com.ufopa.spring.BaseTestContainer;
import com.ufopa.spring.ClienteDataBuilder;
import com.ufopa.spring.config.JpaConfig;
import com.ufopa.spring.dto.ClienteResumoDto;
import com.ufopa.spring.model.Cliente;

@ActiveProfiles("test")
@DataJpaTest
@Import(JpaConfig.class)
public class ClienteRepositoryTest extends BaseTestContainer {

  @Autowired
  private ClienteRepository repository;

  @Test
  void deveriaRetornarResumoDeClientesContendoNome() {
    UUID id1 = repository.save(ClienteDataBuilder.cliente1()).getId();
    UUID id2 = repository.save(ClienteDataBuilder.cliente2()).getId();
    Page<Cliente> clientes = repository.findByNomeContainsIgnoreCaseOrderByNome("TESTE".toLowerCase(), null);
    assertEquals(2L, clientes.get().count());
    assertEquals(id2, clientes.get().findFirst().get().getId());
    assertEquals(id1, clientes.get().skip(1).findFirst().get().getId());
  }

  @Test
  void deveriaRetornarResumoDeClientesContendoEmail() {
    Cliente cliente = ClienteDataBuilder.cliente1();
    UUID id = repository.save(cliente).getId();
    Page<ClienteResumoDto> clientes = repository.findClienteResumoByEmail(
        "@", null);
    assertNotNull(clientes.stream().filter(resumo -> resumo.getId().equals(id)).findAny().get());
  }

  @Test
  void deveriaRetornarVerdadeiroOuFalsoSeExistirAlgumClienteComEmailIgual() {
    Cliente cliente = repository.save(ClienteDataBuilder.cliente1());
    assertTrue(repository.existsByEmailIgnoreCase(cliente.getEmail()));
    repository.delete(cliente);
    assertFalse(repository.existsByEmailIgnoreCase(cliente.getEmail()));
  }

  @Test
  void deveriaRetornarVerdadeiroOuFalsoSeExisterAlgumClienteComTelefoneIgual() {
    Cliente cliente = repository.save(ClienteDataBuilder.cliente1());
    assertTrue(repository.existsByTelefone(cliente.getTelefone()));
    repository.delete(cliente);
    assertFalse(repository.existsByTelefone(cliente.getTelefone()));
  }

}
