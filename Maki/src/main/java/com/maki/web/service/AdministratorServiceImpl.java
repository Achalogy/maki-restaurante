package com.maki.web.service;

import com.maki.web.entities.Administrator;
import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.exception.InvalidCredentialsException;
import com.maki.web.repository.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministratorServiceImpl implements AdministratorService {

  @Autowired
  private AdministratorRepository repo;

  @Override
  public List<Administrator> selectAll() {
    return repo.findAll();
  }

  @Override
  public Administrator selectById(Long id) throws EntityNotFoundException {
    return repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Administrador no encontrado con ID: " + id));
  }

  @Override
  public Administrator insert(Administrator entity) throws EntityConstraintException {
    if (entity.getId() != null) {
      throw new EntityConstraintException("El insert de Administrador no debe incluir un ID");
    }
    return repo.save(entity);
  }

  @Override
  public void delete(Administrator entity) throws EntityNotFoundException {
    deleteByID(entity.getId());
  }

  @Override
  public void deleteByID(Long id) throws EntityNotFoundException {
    if (!repo.existsById(id)) {
      throw new EntityNotFoundException("No se puede eliminar: Administrador no existe con ID: " + id);
    }
    repo.deleteById(id);
  }

  @Override
  public Administrator update(Administrator entity) throws EntityConstraintException, EntityNotFoundException {
    if (entity.getId() == null || !repo.existsById(entity.getId())) {
      throw new EntityNotFoundException("No se puede actualizar: Administrador no encontrado");
    }
    return repo.save(entity);
  }

  @Override
  public boolean existsByUsername(String username) {
    return repo.existsByUsername(username);
  }
}