package com.maki.web.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Tabla unificada de usuarios para autenticación.
 * Todos los tipos de usuario (Client, Operator, Administrator) quedan mapeados aquí.
 *
 * NOTA IMPORTANTE sobre los roles:
 * Esta clase tiene una LISTA de roles (ManyToMany).
 * Para agregar un rol usa: userEntity.getRoles().add(role)
 * NO existe setRole(Role) — usa getRoles().add(role)
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Método auxiliar para agregar un rol a la lista.
     * Evita tener que escribir userEntity.getRoles().add(role) cada vez.
     * Así el Dataloader puede hacer: userEntity.addRole(role)
     */
    public void addRole(Role role) {
        this.roles.add(role);
    }
}