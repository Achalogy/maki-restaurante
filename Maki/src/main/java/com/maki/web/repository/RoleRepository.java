package com.maki.web.repository;

import com.maki.web.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la tabla de roles.
 * Usado en el Dataloader para crear los roles iniciales
 * y en CustomUserDetailService para mapear permisos.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    // Busca un rol por nombre (ej: "CLIENT", "OPERATOR", "ADMIN")
    Optional<Role> findByName(String name);
}