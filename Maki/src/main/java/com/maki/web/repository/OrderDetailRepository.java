package com.maki.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.maki.web.entities.OrderDetails;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetails, Long> {

}
