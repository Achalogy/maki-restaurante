package com.maki.web.service;

import com.maki.web.entities.AdicionalCategoria;
import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.repository.AdicionalCategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class AdicionalCategoriaServiceImpl implements AdicionalCategoriaService {

  @Autowired
  private AdicionalCategoriaRepository repo;

  @Override
  public Collection<AdicionalCategoria> selectAll() {
    return repo.findAll();
  }

  @Override
  public AdicionalCategoria selectById(Long id) throws EntityNotFoundException {
    return repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Relación Adicional-Categoría no encontrada: " + id));
  }

  @Override
  public AdicionalCategoria insert(AdicionalCategoria entity) throws EntityConstraintException {
    if (entity.getId() != null) {
      throw new EntityConstraintException("El insert de AdicionalCategoria no debe tener ID");
    }
    return repo.save(entity);
  }

  @Override
  public void delete(AdicionalCategoria entity) throws EntityNotFoundException {
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
  public AdicionalCategoria update(AdicionalCategoria entity)
      throws EntityConstraintException, EntityNotFoundException {
    if (entity.getId() == null || !repo.existsById(entity.getId())) {
      throw new EntityNotFoundException("No se puede actualizar: El registro no existe");
    }
    return repo.save(entity);
  }

  @Override
  public List<AdicionalCategoria> findByCategoria_Id(Long categoriaId) {
    return repo.findByCategoria_Id(categoriaId);
  }
}