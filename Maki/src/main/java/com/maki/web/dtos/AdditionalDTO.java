package com.maki.web.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdditionalDTO {
    private Long id;
    private String name;
    private double price;
    // No incluye la lista de categorías anidadas
}
