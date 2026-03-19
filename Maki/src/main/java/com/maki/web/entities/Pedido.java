package com.maki.web.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pedido {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = true)
  private java.time.LocalDateTime fechaCreacion;

  @Column(nullable = true)
  private java.time.LocalDateTime fechaEntrega;

  @Column(length = 50)
  private String estado;

  @ManyToOne
  @JoinColumn(name = "cliente_id", nullable = false)
  private Cliente cliente;

  @ManyToOne
  @JoinColumn(name = "domiciliario_id", nullable = true)
  private Domiciliario domiciliario;

  @ManyToOne
  @JoinColumn(name = "operador_id", nullable = true)
  private Operador operador;

  public Pedido(Cliente cliente) {
    this.cliente = cliente;
    this.estado = "PENDIENTE";
    this.fechaCreacion = java.time.LocalDateTime.now();
  }
}