package com.maki.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.maki.web.entities.PlatoAdicional;

@Repository
public interface PlatoAdicionalRepository extends JpaRepository<PlatoAdicional, Long> {
  
}
