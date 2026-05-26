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
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private String phone;

    @Column(length = 20, nullable = false, unique = true)
    private String national_id;

    @Column(nullable = false)
    private boolean available;

    @Column(nullable = false)
    private boolean busy;

    public Delivery(String name, String phone, String national_id, boolean available) {
        this.name = name;
        this.phone = phone;
        this.national_id = national_id;
        this.available = available;
        this.busy = false;
    }

    public Delivery(
            String name, String phone, String national_id, boolean available, boolean busy) {
        this.name = name;
        this.phone = phone;
        this.national_id = national_id;
        this.available = available;
        this.busy = busy;
    }

    public Delivery(Delivery d) {
        this.name = d.name;
        this.phone = d.phone;
        this.national_id = d.national_id;
        this.available = d.available;
        this.busy = d.busy;
    }
}
