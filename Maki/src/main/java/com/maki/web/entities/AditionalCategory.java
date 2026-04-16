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
public class AditionalCategory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

  @ManyToOne
  @JoinColumn(name = "aditional_id", nullable = false)
  private Aditional aditional;

  public AditionalCategory(Long categoryId, Long aditionalId) {
    this.category = new Category();
    this.category.setId(categoryId);
    this.aditional = new Aditional();
    this.aditional.setId(aditionalId);
  }
}