package com.maki.web.service;

import com.maki.web.entities.PlatoAdicional;
import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.repository.PlatoAdicionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PlatoAdicionalServiceImpl implements PlatoAdicionalService {

  @Autowired
  private PlatoAdicionalRepository repo;

  @Override
  public Collection<PlatoAdicional> selectAll() {
    return repo.findAll();
  }

  @Override
  public PlatoAdicional selectById(Long id) throws EntityNotFoundException {
    return repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Relación Plato-Adicional no encontrada: " + id));
  }

  @Override
  public PlatoAdicional insert(PlatoAdicional entity) throws EntityConstraintException {
    if (entity.getId() != null) {
      throw new EntityConstraintException("El insert de PlatoAdicional no debe tener ID");
    }
    return repo.save(entity);
  }

  @Override
  public void delete(PlatoAdicional entity) throws EntityNotFoundException {
    deleteByID(entity.getId());
  }

  @Override
  public void deleteByID(Long id) throws EntityNotFoundException {
    if (!repo.existsById(id)) {
      throw new EntityNotFoundException("No se puede eliminar: ID no encontrado: " + id);
    }
    repo.deleteById(id);
  }

  @Override
  public PlatoAdicional update(PlatoAdicional entity) throws EntityConstraintException, EntityNotFoundException {
    if (entity.getId() == null || !repo.existsById(entity.getId())) {
      throw new EntityNotFoundException("No se puede actualizar: El registro no existe");
    }
    return repo.save(entity);
  }
}