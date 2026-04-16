package com.maki.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maki.web.entities.Delivery;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

}