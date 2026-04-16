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
public class AditionalOrderDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "detail_id", nullable = false)
  private OrderDetails detail;

  @ManyToOne
  @JoinColumn(name = "aditional_id", nullable = false)
  private Aditional aditional;

  public AditionalOrderDetails(OrderDetails detail, Aditional aditional) {
    this.detail = detail;
    this.aditional = aditional;
  }
}