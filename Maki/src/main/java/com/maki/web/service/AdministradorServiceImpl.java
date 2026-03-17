package com.maki.web.service;

import com.maki.web.entities.Administrador;
import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.repository.AdministradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AdministradorServiceImpl implements AdministradorService {

  @Autowired
  private AdministradorRepository repo;

  @Override
  public Collection<Administrador> selectAll() {
    return repo.findAll();
  }

  @Override
  public Administrador selectById(Long id) throws EntityNotFoundException {
    return repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Administrador no encontrado con ID: " + id));
  }

  @Override
  public Administrador insert(Administrador entity) throws EntityConstraintException {
    if (entity.getId() != null) {
      throw new EntityConstraintException("El insert de Administrador no debe incluir un ID");
    }
    return repo.save(entity);
  }

  @Override
  public void delete(Administrador entity) throws EntityNotFoundException {
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
  public Administrador update(Administrador entity) throws EntityConstraintException, EntityNotFoundException {
    if (entity.getId() == null || !repo.existsById(entity.getId())) {
      throw new EntityNotFoundException("No se puede actualizar: Administrador no encontrado");
    }
    return repo.save(entity);
  }
}