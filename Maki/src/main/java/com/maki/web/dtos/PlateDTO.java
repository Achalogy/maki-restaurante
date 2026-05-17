package com.maki.web.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlateDTO {
    private Long id;
    private String name;
    private double price;
    private String description;
    private String urlImage;
    private boolean available;
    private String categoryName; // Solo el nombre, no el objeto Category completo
}
