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
public class Operador {
  @Id
  @Column(name = "id", nullable = false, unique = true)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "nombre", length = 100, nullable = false)
  private String nombre;

  @Column(name="usuario", length = 100, nullable = false, unique = true)
  private String usuario;

  @Column(name="contrasena", length = 100, nullable = false)
  private String contrasena;

  public Operador(String nombre, String usuario, String contrasena) {
    this.nombre = nombre;
    this.usuario = usuario;
    this.contrasena = contrasena;
  }
  
}
