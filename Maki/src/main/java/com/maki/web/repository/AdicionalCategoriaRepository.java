package com.maki.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.maki.web.entities.AdicionalCategoria;
import java.util.List;

@Repository
public interface AdicionalCategoriaRepository extends JpaRepository<AdicionalCategoria, Long> {
  List<AdicionalCategoria> findByCategory_Id(Long categoryId);
  List<AdicionalCategoria> findByAditional_Id(Long aditionalId);
}
