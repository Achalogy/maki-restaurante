package com.maki.web.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
public class Plato {
    @Id
    private Long id;
    
    private String nombre;
    private double precio;
    private String descripcion;
    private String urlImage;
    private boolean disponible;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    Plato(String nombre, double precio, String descripcion, String urlImage, boolean disponible) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.urlImage = urlImage;
        this.disponible = disponible;
    }
}
