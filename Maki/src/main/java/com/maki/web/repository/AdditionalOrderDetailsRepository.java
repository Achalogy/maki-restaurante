package com.maki.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.maki.web.entities.AdditionalOrderDetails;

@Repository
public interface AdditionalOrderDetailsRepository extends JpaRepository<AdditionalOrderDetails, Long> {

}
