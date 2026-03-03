package com.maki.web.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Cliente {
    private Integer id;

    private String nombre;
    private String Apellido;
    private String correo;
    private String contrasena;
    private String telefono;
    private String Direccion;
}
