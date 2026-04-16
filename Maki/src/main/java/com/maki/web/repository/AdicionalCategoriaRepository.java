package com.maki.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.maki.web.entities.AditionalCategory;
import java.util.List;

@Repository
public interface AdicionalCategoriaRepository extends JpaRepository<AditionalCategory, Long> {
  List<AditionalCategory> findByCategory_Id(Long categoryId);
  List<AditionalCategory> findByAditional_Id(Long aditionalId);
}
