package com.maki.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.maki.web.entities.PedidoDetalles;

@Repository
public interface PedidoDetallesRepository extends JpaRepository<PedidoDetalles, Long> {

}
