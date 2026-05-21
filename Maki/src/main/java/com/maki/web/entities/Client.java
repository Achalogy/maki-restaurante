package com.maki.web.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "clientes")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "surname", length = 100, nullable = false)
    private String surname;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    // La contraseña ya NO se guarda aquí, va en UserEntity
    // Se mantiene solo para compatibilidad con el login viejo
    @Transient
    private String password;

    @Column(name = "phone", length = 100, nullable = false)
    private String phone;

    @Column(name = "address", length = 100, nullable = false)
    private String address;

    // Relación con UserEntity - ignorada en JSON para no exponer credenciales
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    public Client(String name, String surname, String email, String password, String phone, String address) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
    }
}