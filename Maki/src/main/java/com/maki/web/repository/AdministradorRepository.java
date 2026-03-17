package com.maki.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maki.web.entities.Administrador;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {

}