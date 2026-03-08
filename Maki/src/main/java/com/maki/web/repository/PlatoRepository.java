package com.maki.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.maki.web.entities.Plato;

@Repository
public interface PlatoRepository extends JpaRepository<Plato, Long> {

}