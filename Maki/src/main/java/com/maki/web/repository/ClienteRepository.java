package com.maki.web.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.maki.web.entities.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

  // NOWAY
  // Spring implementa esto automáticamente al ver el nombre "findBy" + "Correo"
  Optional<Cliente> findByCorreo(String correo);
}