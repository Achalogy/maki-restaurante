package com.maki.web.service;

import com.maki.web.entities.Categoria;
import com.maki.web.entities.Plato;
import com.maki.web.repository.PlatoRepository;
import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.Collection;

@Service
public class PlatoServiceImpl implements PlatoService {

  @Autowired
  private PlatoRepository repo;

  @Override
  public Collection<Plato> selectAll() {
    return repo.findAll();
  }

  @Override
  public Plato selectById(Long id) throws EntityNotFoundException {
    return repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Plato no encontrado con ID: " + id));
  }

  @Override
  public Plato insert(Plato entity) throws EntityConstraintException {
    if (entity.getId() != null) {
      throw new EntityConstraintException("El insert de Plato no debe incluir un ID");
    }
    return repo.save(entity);
  }

  @Override
  @Transactional
  public Plato update(Plato entity) throws EntityConstraintException, EntityNotFoundException {
    if (entity.getId() == null || !repo.existsById(entity.getId())) {
      throw new EntityNotFoundException("No se puede actualizar: Plato no existe");
    }
    return repo.save(entity);
  }

  @Override
  public void delete(Plato entity) throws EntityNotFoundException {
    if (entity == null || entity.getId() == null) {
      throw new EntityNotFoundException("Plato inválido para eliminar");
    }
    this.deleteByID(entity.getId());
  }

  @Override
  @Transactional
  public void deleteByID(Long id) throws EntityNotFoundException {
    if (!repo.existsById(id)) {
      throw new EntityNotFoundException("No se puede eliminar: Plato no encontrado con ID: " + id);
    }
    repo.deleteById(id);
  }

  @Override
  @Transactional
  public void cambiarCategoria(Categoria categoria, Long platoId) throws EntityNotFoundException {
    Plato plato = this.selectById(platoId);

    plato.setCategoria(categoria);
    
    repo.save(plato);
  }
}