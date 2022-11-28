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

@SuppressWarnings("squid:S1610")
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ClienteMapper {

  public abstract ClienteResumoDto clienteToResumoDto(Cliente cliente);

  @Mapping(source = "dataAlteracao", target = "ultimaModificacao")
  public abstract ClienteDetalheDto clienteToDetalheDto(Cliente cliente);

  @Mapping(source = "enviaEmail", target = "enviaEmail", defaultValue = "false")
  @Mapping(source = "rendaMedia", target = "rendaMedia", defaultValue = "0")
  public abstract Cliente clienteFromDto(ClienteInputDto cliente);

  public abstract Cliente clienteFromDto(ClienteInputDto detalheDto, @MappingTarget Cliente cliente);

}
