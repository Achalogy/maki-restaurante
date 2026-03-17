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
public class AdicionalCategoria {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "categoria_id", nullable = false)
  private Categoria categoria;

  @ManyToOne
  @JoinColumn(name = "adicional_id", nullable = false)
  private Adicional adicional;

  public AdicionalCategoria(Long categoriaId, Long adicionalId) {
    this.categoria = new Categoria();
    this.categoria.setId(categoriaId);
    this.adicional = new Adicional();
    this.adicional.setId(adicionalId);
  }
}