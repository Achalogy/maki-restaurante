package com.maki.web.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cliente {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;
    @Column(name = "apellido", length = 100, nullable = false)
    private String apellido;
    @Column(name = "correo", length = 100, nullable = false, unique = true)
    private String correo;
    @Column(name = "contrasena", length = 100, nullable = false, unique = true)
    private String contrasena;
    @Column(name = "telefono", length = 100, nullable = false)
    private String telefono;
    @Column(name = "direccion", length = 100, nullable = false)
    private String direccion;

    public Cliente(String correo, String contrasena) {
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public Cliente(String nombre, String apellido, String correo, String contrasena, String telefono, String direccion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contrasena = contrasena;
        this.telefono = telefono;
        this.direccion = direccion;
    }
}
