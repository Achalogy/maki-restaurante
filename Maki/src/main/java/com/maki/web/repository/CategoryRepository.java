package com.maki.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.maki.web.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    
} 