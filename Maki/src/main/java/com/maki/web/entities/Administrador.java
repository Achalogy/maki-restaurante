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
public class Administrador {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 100, nullable = false)
  private String nombre;

  @Column(length = 100, nullable = false, unique = true)
  private String usuario;

  @Column(length = 100, nullable = false)
  private String contrasena;

  public Administrador(String nombre, String usuario, String contrasena) {
    this.nombre = nombre;
    this.usuario = usuario;
    this.contrasena = contrasena;
  }
}