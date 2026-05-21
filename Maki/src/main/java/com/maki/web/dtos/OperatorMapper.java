package com.maki.web.dtos;

import com.maki.web.entities.Operator;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Convierte Operator -> OperatorDTO.
 * Omite password automáticamente porque OperatorDTO no lo tiene.
 */
@Mapper
public interface OperatorMapper {
    OperatorMapper INSTANCE = Mappers.getMapper(OperatorMapper.class);

    OperatorDTO toDTO(Operator operator);
}