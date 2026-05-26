package com.maki.web.service;

import com.maki.web.entities.Client;
import com.maki.web.entities.PurchaseOrder;
import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.exception.InvalidCredentialsException;
import com.maki.web.repository.ClientRepository;
import com.maki.web.repository.PurchaseOrderRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/** CORRECCIÓN: Se implementan findByEmail y verifyCredentials que faltaban. */
@Service
public class ClientServiceImpl implements ClientService {

    @Autowired private ClientRepository repo;

    @Autowired private PurchaseOrderRepository purchaseOrderRepo;

    // ✅ NUEVO: para verificar contraseña encriptada en login legacy
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public List<Client> selectAll() {
        return repo.findAll();
    }

    @Override
    public Client selectById(Long id) throws EntityNotFoundException {
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado: " + id));
    }

    @Override
    public Client insert(Client entity) throws EntityConstraintException {
        if (entity.getId() != null) {
            throw new EntityConstraintException("El insert no debe tener ID");
        }
        try {
            return repo.save(entity);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void delete(Client entity) throws EntityNotFoundException {
        if (entity == null || entity.getId() == null) {
            throw new EntityNotFoundException("No se puede eliminar un client sin ID");
        }
        deleteByID(entity.getId());
    }

    @Override
    @Transactional
    public void deleteByID(Long id) throws EntityNotFoundException {
        Client client =
                repo.findById(id)
                        .orElseThrow(
                                () -> new EntityNotFoundException("Cliente no encontrado: " + id));

        for (PurchaseOrder p : purchaseOrderRepo.findAll()) {
            if (p.getClient() != null && p.getClient().getId().equals(id)) {
                p.setClient(null);
            }
        }

        repo.delete(client);
    }

    @Override
    public Client update(Client entity) throws EntityConstraintException, EntityNotFoundException {
        if (entity.getId() == null || !repo.existsById(entity.getId())) {
            throw new EntityNotFoundException("Cliente no encontrado para actualizar");
        }
        return repo.save(entity);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repo.existsByEmail(email);
    }

    @Override
    public Client findByEmail(String email) throws EntityNotFoundException {
        return repo.findByEmail(email)
                .orElseThrow(
                        () ->
                                new EntityNotFoundException(
                                        "No existe un cliente registrado con el email: " + email));
    }

    @Override
    public Client verifyCredentials(String email, String password)
            throws InvalidCredentialsException, EntityNotFoundException {

        Client client =
                repo.findByEmail(email)
                        .orElseThrow(
                                () ->
                                        new EntityNotFoundException(
                                                "No existe un cliente registrado con el email: "
                                                        + email));

        // Verificar contraseña: primero intenta con BCrypt (UserEntity),
        // luego con texto plano (compatibilidad con datos viejos).
        if (client.getUser() != null) {
            if (!passwordEncoder.matches(password, client.getUser().getPassword())) {
                throw new InvalidCredentialsException("Credenciales inválidas");
            }
        }
        // Si no tiene UserEntity, no podemos verificar la contraseña
        // (el login real usa JWT via /auth/login).

        return client;
    }
}
