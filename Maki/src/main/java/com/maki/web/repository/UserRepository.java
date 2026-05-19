package com.maki.web.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maki.web.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    Boolean existsByUsername(String username);
}
