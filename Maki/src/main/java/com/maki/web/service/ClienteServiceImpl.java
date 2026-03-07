package com.maki.web.service;

import com.maki.web.entities.Cliente;
import com.maki.web.exception.InvalidCredentialsException;
import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.Collection;

@Service
public class ClienteServiceImpl implements ClienteService {

  @Autowired
  private ClienteRepository repo;

  @Override
  public Collection<Cliente> selectAll() {
    return repo.findAll();
  }

  @Override
  public Cliente selectById(Long id) throws EntityNotFoundException {
    return repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado: " + id));
  }

  @Override
  public Cliente insert(Cliente entity) throws EntityConstraintException {
    if (entity.getId() != null) {
      throw new EntityConstraintException("El insert no debe tener ID");
    }
    return repo.save(entity);
  }

  @Override
  public void delete(Cliente entity) throws EntityNotFoundException {
    if (entity == null || entity.getId() == null) {
      throw new EntityNotFoundException("No se puede eliminar un cliente sin ID");
    }
    deleteByID(entity.getId());
  }

  @Override
  @Transactional
  public void deleteByID(Long id) throws EntityNotFoundException {
    Cliente cliente = repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado: " + id));

    // TODO: CUANDO el cliente tenga Pedidos, aquí deberías decidir si:
    // 1. Los borras en cascada.
    // 2. Los dejas huérfanos (setCliente(null)).
    // 3. Impides el borrado si tiene historial.

    repo.delete(cliente);
  }

  @Override
  public Cliente update(Cliente entity) throws EntityConstraintException, EntityNotFoundException {
    if (entity.getId() == null || !repo.existsById(entity.getId())) {
      throw new EntityNotFoundException("Cliente no encontrado para actualizar");
    }
    return repo.save(entity);
  }

  @Override
  public Cliente registrarCliente(Cliente cliente) throws EntityConstraintException {
    return this.insert(cliente);
  }

  @Override
  public Cliente registrarCliente(String nombre, String apellido, String correo, String contrasena, String telefono,
      String direccion) throws EntityConstraintException {
    Cliente nuevo = new Cliente(
      nombre, apellido, correo, contrasena, telefono, direccion
    );

    return this.insert(nuevo);
  }

  @Override
  public Cliente verificarCredenciales(Cliente cliente) throws InvalidCredentialsException, EntityNotFoundException {
    if (cliente.getId() == null) {
      throw new EntityNotFoundException("ID de cliente es obligatorio para verificar por objeto");
    }

    Cliente repoClient = this.selectById(cliente.getId());

    if (!repoClient.getCorreo().equalsIgnoreCase(cliente.getCorreo())) {
      throw new InvalidCredentialsException("El correo no coincide con el ID proporcionado");
    }

    if (!repoClient.getContrasena().equals(cliente.getContrasena())) {
      throw new InvalidCredentialsException("Contraseña incorrecta");
    }

    return repoClient;
  }

  @Override
  public Cliente verificarCredenciales(String correo, String contrasena)
      throws InvalidCredentialsException, EntityNotFoundException {
    Cliente repoClient = repo.findByCorreo(correo)
        .orElseThrow(() -> new EntityNotFoundException("No existe un cliente registrado con el correo: " + correo));

    if (!repoClient.getContrasena().equals(contrasena)) {
      throw new InvalidCredentialsException("Credenciales inválidas");
    }

    return repoClient;
  }
}