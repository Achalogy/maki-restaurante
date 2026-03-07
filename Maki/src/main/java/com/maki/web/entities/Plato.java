package com.maki.web.entities;

import jakarta.persistence.Column;
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
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;
    
    @Column(name = "precio", nullable = false)
    private double precio;
    @Column(name = "descripcion", length = 1500, nullable = false)
    private String descripcion;
    @Column(name = "urlImage", length = 500, nullable = false)
    private String urlImage;
    @Column(name = "disponible", nullable = false)
    private boolean disponible;
    
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    public Plato(String nombre, double precio, String descripcion, String urlImage, boolean disponible) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.urlImage = urlImage;
        this.disponible = disponible;
    }
}
