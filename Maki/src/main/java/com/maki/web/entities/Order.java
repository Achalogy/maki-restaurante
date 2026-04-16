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
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = true)
  private java.time.LocalDateTime creation_date;

  @Column(nullable = true)
  private java.time.LocalDateTime delivery_dat;

  @Column(length = 50)
  private String status;

  @ManyToOne
  @JoinColumn(name = "client_id", nullable = true)
  private Client client;

  @ManyToOne
  @JoinColumn(name = "delivery_id", nullable = true)
  private Delivery delivery;

  @ManyToOne
  @JoinColumn(name = "operator_id", nullable = true)
  private Operator operator;

  public Order(Client client) {
    this.client = client;
    this.status = "PENDING";
    this.creation_date = java.time.LocalDateTime.now();
  }
}