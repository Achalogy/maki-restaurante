package com.maki.web.entities;

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
public class AdicionalPedidoDetalles {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "detalle_id", nullable = false)
  private PedidoDetalles detalle;

  @ManyToOne
  @JoinColumn(name = "aditional_id", nullable = false)
  private Adicional aditional;

  public AdicionalPedidoDetalles(PedidoDetalles detalle, Adicional aditional) {
    this.detalle = detalle;
    this.aditional = aditional;
  }
}