package com.maki.web.service;

import com.maki.web.entities.Cliente;
import com.maki.web.exception.InvalidCredentialsException;
import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements  ClienteService{

  @Autowired
  ClienteRepository repo;

  @Override
  public Cliente registrarCliente(Cliente cliente) throws EntityConstraintException {
    return this.repo.insert(cliente);
  }

  @Override
  public Cliente registrarCliente(String nombre, String apellido, String correo, String contrasena, String telefono, String direccion) throws EntityConstraintException {
    return this.repo.insert(
            new Cliente(null, nombre, apellido, correo, contrasena, telefono, direccion)
    );
  }

  @Override
  public Cliente verificarCredenciales(Cliente cliente) throws InvalidCredentialsException, EntityNotFoundException {
    if(cliente.getId() == null) throw new EntityNotFoundException("No hay cliente con id nulo");
    Cliente repoClient = repo.selectById(cliente.getId());

    if(!repoClient.getCorreo().equals(cliente.getCorreo())) throw new EntityNotFoundException("No hay cliente con correo="+cliente.getCorreo());

    // TODO: OBVIAMENTE HAY QUE CAMBIAR ESTO POR UN JSON WEB TOKEN O ALGO ASÍ
    if(!repoClient.getContrasena().equals(cliente.getContrasena())) throw  new InvalidCredentialsException("Crednciales invalidas");
    return repoClient;
  }

  @Override
  public Cliente verificarCredenciales(String correo, String contrasena) throws InvalidCredentialsException, EntityNotFoundException {
    Optional<Cliente> cliente = this.selectAll().stream().filter(c -> c.getCorreo().equals(correo)).findFirst();

    if(cliente.isEmpty()) throw new EntityNotFoundException("No hay cliente con correo="+correo);

    Cliente repoClient = repo.selectById(cliente.get().getId());

    // TODO: OBVIAMENTE HAY QUE CAMBIAR ESTO POR UN JSON WEB TOKEN O ALGO ASÍ
    if(!repoClient.getContrasena().equals(cliente.get().getContrasena())) throw  new InvalidCredentialsException("Crednciales invalidas");
    return repoClient;
  }

  @Override
  public Collection<Cliente> selectAll() {
    return repo.selectAll();
  }

  @Override
  public Cliente selectById(Integer id) throws EntityNotFoundException {
    return repo.selectById(id);
  }

  @Override
  public Cliente insert(Cliente entity) throws EntityConstraintException {
    return repo.insert(entity);
  }

  @Override
  public void delete(Cliente entity) throws EntityNotFoundException {
    repo.delete(entity);
  }

  @Override
  public void deleteByID(Integer id) throws EntityNotFoundException {
    repo.deleteByID(id);
  }

  @Override
  public Cliente update(Cliente entity) throws EntityConstraintException, EntityNotFoundException {
    return repo.update(entity);
  }

  @Override
  public Cliente upsert(Cliente entity) throws EntityConstraintException {
    return repo.upsert(entity);
  }
}
