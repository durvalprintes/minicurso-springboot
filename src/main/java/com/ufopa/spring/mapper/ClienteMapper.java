package com.ufopa.spring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.ufopa.spring.dto.ClienteDetalheDto;
import com.ufopa.spring.dto.ClienteInputDto;
import com.ufopa.spring.dto.ClienteResumoDto;
import com.ufopa.spring.model.Cliente;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClienteMapper {

  ClienteResumoDto clienteToResumoDto(Cliente cliente);

  @Mapping(source = "modifiedDate", target = "ultimaModificacao")
  ClienteDetalheDto clienteToDetalheDto(Cliente cliente);

  @Mapping(source = "enviaEmail", target = "enviaEmail", defaultValue = "false")
  @Mapping(source = "rendaMedia", target = "rendaMedia", defaultValue = "0")
  Cliente clienteFromDto(ClienteInputDto cliente);

  Cliente clienteFromDto(ClienteInputDto detalheDto, @MappingTarget Cliente cliente);

}
