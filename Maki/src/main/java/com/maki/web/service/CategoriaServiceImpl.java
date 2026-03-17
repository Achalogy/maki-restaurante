package com.maki.web.service;

import com.maki.web.entities.Categoria;
import com.maki.web.repository.CategoriaRepository;

import jakarta.transaction.Transactional;

import com.maki.web.entities.Plato;
import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CategoriaServiceImpl implements CategoriaService {

  @Autowired
  CategoriaRepository repo;

  @Autowired
  PlatoService platoService;

  @Override
  public Collection<Categoria> selectAll() {
    return repo.findAll();
  }

  @Override
  public Categoria selectById(Long id) throws EntityNotFoundException {
    return repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Categoria no encontrada: " + id));
  }

  @Override
  public Categoria insert(Categoria entity) throws EntityConstraintException {
    if (entity.getId() != null) {
      throw new EntityConstraintException("Insert no debe tener ID");
    }
    return repo.save(entity);
  }

  @Override
  public void delete(Categoria entity) throws EntityNotFoundException {
    deleteByID(entity.getId());
  }

  @Override
  @Transactional
  public void deleteByID(Long id) throws EntityNotFoundException {

    Categoria categoria = repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Categoria no encontrada: " + id));

    Categoria none = repo.findById(1L)
        .orElseThrow(() -> new EntityNotFoundException("Categoria 'None' no existe"));

    for (Plato p : platoService.selectAll()) {
      if (p.getCategoria() != null && p.getCategoria().getId().equals(id)) {
        p.setCategoria(none);
        platoService.update(p);
      }
    }

    repo.delete(categoria);
  }

  @Override
  public Categoria update(Categoria entity) throws EntityConstraintException, EntityNotFoundException {
    if (entity.getId() == null || !repo.existsById(entity.getId())) {
      throw new EntityNotFoundException("Categoria no encontrada");
    }

    return repo.save(entity);
  }
}
