package com.maki.web.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private java.time.LocalDateTime creation_date;

    @Column(nullable = true)
    private java.time.LocalDateTime delivery_date;

    @Column(length = 50)
    private String status;

    // Solo muestra id, name, email del cliente — no su contraseña ni relaciones
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = true)
    @JsonIgnoreProperties({"password", "user", "address", "phone", "surname"})
    private Client client;

    // Solo muestra id y name del delivery
    @ManyToOne
    @JoinColumn(name = "delivery_id", nullable = true)
    @JsonIgnoreProperties({"national_id", "busy"})
    private Delivery delivery;

    // Solo muestra id y name del operador
    @ManyToOne
    @JoinColumn(name = "operator_id", nullable = true)
    @JsonIgnoreProperties({"password", "user"})
    private Operator operator;

    public PurchaseOrder(Client client) {
        this.client = client;
        this.status = "pending";
        this.creation_date = java.time.LocalDateTime.now();
    }
}