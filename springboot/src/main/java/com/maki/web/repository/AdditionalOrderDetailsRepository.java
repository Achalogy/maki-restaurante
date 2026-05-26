package com.maki.web.repository;

import com.maki.web.entities.AdditionalOrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdditionalOrderDetailsRepository
        extends JpaRepository<AdditionalOrderDetails, Long> {}
