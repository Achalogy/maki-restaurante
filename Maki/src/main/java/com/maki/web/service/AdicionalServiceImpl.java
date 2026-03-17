package com.maki.web.service;

import com.maki.web.entities.Adicional;
import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.repository.AdicionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AdicionalServiceImpl implements AdicionalService {

  @Autowired
  private AdicionalRepository repo;

  @Override
  public Collection<Adicional> selectAll() {
    return repo.findAll();
  }

  @Override
  public Adicional selectById(Long id) throws EntityNotFoundException {
    return repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Adicional no encontrado con ID: " + id));
  }

  @Override
  public Adicional insert(Adicional entity) throws EntityConstraintException {
    if (entity.getId() != null) {
      throw new EntityConstraintException("El insert de Adicional no debe incluir un ID");
    }
    return repo.save(entity);
  }

  @Override
  public void delete(Adicional entity) throws EntityNotFoundException {
    deleteByID(entity.getId());
  }

  @Override
  public void deleteByID(Long id) throws EntityNotFoundException {
    if (!repo.existsById(id)) {
      throw new EntityNotFoundException("No se puede eliminar: Adicional no existe con ID: " + id);
    }
    repo.deleteById(id);
  }

  @Override
  public Adicional update(Adicional entity) throws EntityConstraintException, EntityNotFoundException {
    if (entity.getId() == null || !repo.existsById(entity.getId())) {
      throw new EntityNotFoundException("No se puede actualizar: Adicional no encontrado");
    }
    return repo.save(entity);
  }
}