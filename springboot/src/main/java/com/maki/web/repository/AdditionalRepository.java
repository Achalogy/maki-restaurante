package com.maki.web.repository;

import com.maki.web.entities.Additional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdditionalRepository extends JpaRepository<Additional, Long> {}
