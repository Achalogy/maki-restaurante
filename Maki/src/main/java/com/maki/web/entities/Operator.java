package com.maki.web.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Operator {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "username", length = 100, nullable = false, unique = true)
    private String username;

    // La contraseña ya NO se guarda aquí, va en UserEntity
    @Transient
    private String password;

    // Relación con UserEntity - ignorada en JSON
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    public Operator(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public Operator(Long id, String name, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }
}