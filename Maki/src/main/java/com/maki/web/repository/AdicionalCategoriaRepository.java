package com.maki.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.maki.web.entities.AdicionalCategoria;

@Repository
public interface AdicionalCategoriaRepository extends JpaRepository<AdicionalCategoria, Long> {
  
}
