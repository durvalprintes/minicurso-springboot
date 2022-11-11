package com.ufopa.spring.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ufopa.spring.dto.ClienteDetalheDto;
import com.ufopa.spring.dto.ClienteInputDto;
import com.ufopa.spring.dto.ClienteResumoDto;
import com.ufopa.spring.exception.ResourceNotFoundException;
import com.ufopa.spring.exception.SearchException;
import com.ufopa.spring.service.ClienteService;
import com.ufopa.spring.validation.OnInsert;
import com.ufopa.spring.validation.OnUpdate;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

@Api(tags = "Endpoints de cliente")
@ApiResponses(@ApiResponse(code = 401, message = "Necessário autenticar com um usuário"))
@RestController
@RequestMapping(path = "/clientes")
public class ClienteController {

  @Autowired
  ClienteService clienteService;

  @ApiOperation("Retornar lista com resumo de todos os clientes cadastrados")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Retorna a lista com resumo dos clientes", response = ClienteResumoDto.class),
      @ApiResponse(code = 204, message = "Nenhum cliente foi encontrado")
  })
  @GetMapping
  ResponseEntity<List<ClienteResumoDto>> listarClientes() {
    List<ClienteResumoDto> clientes = clienteService.getClientes();
    return clientes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(clientes);
  }

  @ApiOperation("Retornar detalhes do cliente cadastrado")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Retorna os detalhes do cliente", response = ClienteDetalheDto.class),
      @ApiResponse(code = 204, message = "Cliente não foi encontrado"),
      @ApiResponse(code = 404, message = "ID do cliente não encontrado")
  })
  @GetMapping(value = "/{id}")
  ResponseEntity<ClienteDetalheDto> listarCliente(@PathVariable("id") UUID id) throws ResourceNotFoundException {
    return ResponseEntity.ok(clienteService.getCliente(id));
  }

  @ApiOperation("Cadastrar um novo cliente")
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Cliente foi inserido", responseHeaders = {
          @ResponseHeader(name = "location", description = "Endpoint com ID criado para consultar detalhes do cliente", response = String.class) }),
      @ApiResponse(code = 400, message = "Dados de entrada estão incorretos"),
      @ApiResponse(code = 403, message = "Usuário autenticado não tem permissão")
  })
  @ResponseStatus(value = HttpStatus.CREATED)
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Object> inserirCliente(@RequestBody @Validated(OnInsert.class) ClienteInputDto cliente) {
    return ResponseEntity.created(ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(clienteService.saveCliente(cliente))
        .toUri()).build();
  }

  @ApiOperation("Atualizar um cliente cadastrado")
  @ApiResponses(value = {
      @ApiResponse(code = 204, message = "Cliente foi atualizado"),
      @ApiResponse(code = 400, message = "Dados de entrada estão incorretos"),
      @ApiResponse(code = 403, message = "Usuário autenticado não tem permissão"),
      @ApiResponse(code = 404, message = "ID do cliente não encontrado")
  })
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  void alterarCliente(@PathVariable("id") UUID id,
      @RequestBody @Validated(OnUpdate.class) ClienteInputDto cliente) throws ResourceNotFoundException {
    clienteService.updateCliente(id, cliente);
  }

  @ApiOperation("Excluir um cliente cadastrado")
  @ApiResponses(value = {
      @ApiResponse(code = 204, message = "Cliente foi excluído"),
      @ApiResponse(code = 403, message = "Usuário autenticado não tem permissão"),
      @ApiResponse(code = 404, message = "ID do cliente não encontrado")
  })
  @DeleteMapping(value = "/{id}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  void excluirCliente(@PathVariable("id") UUID id) throws ResourceNotFoundException {
    clienteService.deleteCliente(id);
  }

  @GetMapping(value = "/busca")
  ResponseEntity<Page<ClienteResumoDto>> buscarClientes(@RequestParam(required = false, defaultValue = "") String nome,
      @RequestParam(required = false, defaultValue = "") String email, Pageable pageable) throws SearchException {
    return ResponseEntity.ok(clienteService.getClientes(nome, email, pageable));
  }

}
