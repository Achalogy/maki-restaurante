package com.maki.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.maki.web.entities.Adicional;

@Repository
public interface AdicionalRepository extends JpaRepository<Adicional, Long> {
  
}
