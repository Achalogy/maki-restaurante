package com.maki.web.repository;

import com.maki.web.entities.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la tabla de roles. Usado en el Dataloader para crear los roles iniciales y en
 * CustomUserDetailService para mapear permisos.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    // Busca un rol por nombre (ej: "CLIENT", "OPERATOR", "ADMIN")
    Optional<Role> findByName(String name);
}
