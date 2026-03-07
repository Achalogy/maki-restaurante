package com.maki.web.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
public class Cliente {
    @Id
    private Integer id;

    private String nombre;
    private String Apellido;
    private String correo;
    private String contrasena;
    private String telefono;
    private String Direccion;

    public Cliente(String correo, String contrasena) {
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public Cliente() {}
}
