package com.maki.web.repository;

import com.maki.web.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la tabla de usuarios unificada.
 * Spring Security lo usa a través de CustomUserDetailService
 * para verificar credenciales al hacer login.
 */
@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    // Spring genera automáticamente la consulta por el nombre del método
    Optional<UserEntity> findByUsername(String username);

    boolean existsByUsername(String username);
}