package com.maki.web.dtos;

import com.maki.web.entities.Plate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Convierte Plate -> PlateDTO.
 * Extrae solo el nombre de la categoría para no exponer
 * el objeto Category completo con sus relaciones anidadas.
 */
@Mapper
public interface PlateMapper {
    PlateMapper INSTANCE = Mappers.getMapper(PlateMapper.class);

    @Mapping(source = "category.name", target = "categoryName")
    PlateDTO toDTO(Plate plate);
}