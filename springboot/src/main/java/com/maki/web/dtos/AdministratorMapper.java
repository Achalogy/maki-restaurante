package com.maki.web.dtos;

import com.maki.web.entities.Administrator;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Convierte Administrator -> AdministratorDTO. MapStruct omite automáticamente el campo password
 * porque AdministratorDTO no lo tiene.
 */
@Mapper
public interface AdministratorMapper {
    AdministratorMapper INSTANCE = Mappers.getMapper(AdministratorMapper.class);

    AdministratorDTO toDTO(Administrator administrator);
}
