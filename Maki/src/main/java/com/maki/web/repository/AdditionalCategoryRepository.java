package com.maki.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.maki.web.entities.AdditionalCategory;
import java.util.List;

@Repository
public interface AdditionalCategoryRepository extends JpaRepository<AdditionalCategory, Long> {
  List<AdditionalCategory> findByCategory_Id(Long categoryId);
  List<AdditionalCategory> findByAdditional_Id(Long additionalId);
}
