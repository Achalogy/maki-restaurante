package com.maki.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.maki.web.entities.AdicionalPedidoDetalles;

@Repository
public interface AdicionalPedidoDetallesRepository extends JpaRepository<AdicionalPedidoDetalles, Long> {

}
