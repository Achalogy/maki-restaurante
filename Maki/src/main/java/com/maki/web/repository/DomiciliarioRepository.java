package com.maki.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maki.web.entities.Domiciliario;

@Repository
public interface DomiciliarioRepository extends JpaRepository<Domiciliario, Long> {

}