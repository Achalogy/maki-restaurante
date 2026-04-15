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
  @JoinColumn(name = "category_id", nullable = false)
  private Categoria category;

  @ManyToOne
  @JoinColumn(name = "aditional_id", nullable = false)
  private Adicional aditional;

  public AdicionalCategoria(Long categoryId, Long aditionalId) {
    this.category = new Categoria();
    this.category.setId(categoryId);
    this.aditional = new Adicional();
    this.aditional.setId(aditionalId);
  }
}