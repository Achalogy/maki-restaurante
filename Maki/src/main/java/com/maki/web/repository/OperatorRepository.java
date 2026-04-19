package com.maki.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.maki.web.entities.Operator;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Long> {

}
