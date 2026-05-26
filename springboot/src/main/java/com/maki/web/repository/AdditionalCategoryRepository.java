package com.maki.web.repository;

import com.maki.web.entities.AdditionalCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdditionalCategoryRepository extends JpaRepository<AdditionalCategory, Long> {
    List<AdditionalCategory> findByCategory_Id(Long categoryId);

    List<AdditionalCategory> findByAdditional_Id(Long additionalId);
}
