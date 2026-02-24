package com.maki.web.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Plato {
    private Integer id;
    private String nombre;
    private double precio;
    private String descripcion;
    private String urlImage;
    private boolean disponible;
    private String tag;
}
