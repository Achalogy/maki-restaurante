package com.maki.web.service;

import com.maki.web.entities.AditionalCategory;
import com.maki.web.entities.Category;
import com.maki.web.repository.CategoriaRepository;

import jakarta.transaction.Transactional;

import com.maki.web.entities.Plate;
import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

  @Autowired
  CategoriaRepository repo;

  @Autowired
  PlatoService platoService;

  @Autowired
  AdicionalCategoriaService adicionalCategoriaService;

  @Override
  public List<Category> selectAll() {
    return repo.findAll();
  }

  @Override
  public Category selectById(Long id) throws EntityNotFoundException {
    return repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Categoria no encontrada: " + id));
  }

  @Override
  public Category insert(Category entity) throws EntityConstraintException {
    if (entity.getId() != null) {
      throw new EntityConstraintException("Insert no debe tener ID");
    }
    return repo.save(entity);
  }

  @Override
  public void delete(Category entity) throws EntityNotFoundException {
    deleteByID(entity.getId());
  }

  @Override
  @Transactional
  public void deleteByID(Long id) throws EntityNotFoundException {

    Category categoria = repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Categoria no encontrada: " + id));

    for (Plate p : platoService.selectAll()) {
      if (p.getCategory() != null && p.getCategory().getId().equals(id)) {
        platoService.delete(p);
      }
    }
    for (AditionalCategory p : adicionalCategoriaService.selectAll()) {
      if (p.getCategory() != null && p.getCategory().getId().equals(id)) {
        adicionalCategoriaService.delete(p);
      }
    }

    repo.delete(categoria);
  }

  @Override
  public Category update(Category entity) throws EntityConstraintException, EntityNotFoundException {
    if (entity.getId() == null || !repo.existsById(entity.getId())) {
      throw new EntityNotFoundException("Categoria no encontrada");
    }

    return repo.save(entity);
  }
}
