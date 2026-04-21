package com.maki.web.service;

import com.maki.web.entities.Client;
import com.maki.web.entities.PurchaseOrder;
import com.maki.web.exception.InvalidCredentialsException;
import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.repository.ClientRepository;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

  private final PurchaseOrderServiceImpl orderServiceImpl;
  @Autowired
  private ClientRepository repo;

  ClientServiceImpl(PurchaseOrderServiceImpl orderServiceImpl) {
    this.orderServiceImpl = orderServiceImpl;
  }

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
      throw new EntityConstraintException("Ya existe un usuario con este email");
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
    Client client = repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado: " + id));

    for (PurchaseOrder p : orderServiceImpl.selectAll()) {
      if (p.getClient() != null && p.getClient().getId().equals(id)) {
        p.setClient(null);
        orderServiceImpl.update(p);
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
  public Client registerClient(Client client) throws EntityConstraintException {
    return this.insert(client);
  }

  @Override
  public Client registerClient(String name, String username, String email, String password, String phone,
      String direccion) throws EntityConstraintException {
    Client nuevo = new Client(
        name, username, email, password, phone, direccion);

    return this.insert(nuevo);
  }

  @Override
  public Client verifyCredentials(Client client) throws InvalidCredentialsException, EntityNotFoundException {
    if (client.getId() == null) {
      throw new EntityNotFoundException("ID de client es obligatorio para verificar por objeto");
    }

    Client repoClient = this.selectById(client.getId());

    if (!repoClient.getEmail().equalsIgnoreCase(client.getEmail())) {
      throw new InvalidCredentialsException("El Email no coincide con el ID proporcionado");
    }

    if (!repoClient.getPassword().equals(client.getPassword())) {
      throw new InvalidCredentialsException("Contraseña incorrecta");
    }

    return repoClient;
  }

  @Override
  public Client verifyCredentials(String email, String password)
      throws InvalidCredentialsException, EntityNotFoundException {
    Client repoClient = repo.findByEmail(email)
        .orElseThrow(() -> new EntityNotFoundException("No existe un client registrado con el email: " + email));

    if (!repoClient.getPassword().equals(password)) {
      throw new InvalidCredentialsException("Credenciales inválidas");
    }

    return repoClient;
  }
}