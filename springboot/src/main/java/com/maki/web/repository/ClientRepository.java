package com.maki.web.repository;

import com.maki.web.entities.Client;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    // NOWAY
    // Spring implementa esto automáticamente al ver el nombre "findBy" + "Correo"
    Optional<Client> findByEmail(String email);

    Boolean existsByEmail(String email);
}
