package com.maki.web.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "order_id", nullable = false)
  private PurchaseOrder order;

  @ManyToOne
  @JoinColumn(name = "plate_id", nullable = false)
  private Plate plate; 

  @Column(nullable = false)
  private int quantity;

  @OneToMany(mappedBy = "detail", fetch = FetchType.EAGER)
  private List<AdditionalOrderDetails> additionals;

  public OrderDetails(PurchaseOrder order, Plate plate, int quantity) {
    this.order = order;
    this.plate = plate;
    this.quantity = quantity;
  }
}