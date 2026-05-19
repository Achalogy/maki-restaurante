package com.maki.web.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maki.web.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}