package com.maki.web.dtos;

import com.maki.web.entities.Client;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/** Convierte Client -> ClientDTO. Omite password automáticamente porque ClientDTO no lo tiene. */
@Mapper
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    ClientDTO toDTO(Client client);
}
