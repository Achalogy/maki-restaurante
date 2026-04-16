package com.maki.web.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.maki.web.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

  // NOWAY
  // Spring implementa esto automáticamente al ver el nombre "findBy" + "Correo"
  Optional<Client> findByEmail(String email);
}