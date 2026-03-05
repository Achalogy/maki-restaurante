package com.maki.web.service;

import com.maki.web.entities.Categoria;
import com.maki.web.repository.CategoriaRepository;
import com.maki.web.service.PlatoService;
import com.maki.web.entities.Plato;
import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CategoriaServiceImpl implements categoriaService {

  @Autowired
  CategoriaRepository repo;

  @Autowired
  PlatoService platoService;

  @Override
  public Collection<Categoria> selectAll() {
    return repo.selectAll();
  }

  @Override
  public Categoria selectById(Integer id) throws EntityNotFoundException {
    return repo.selectById(id);
  }

  @Override
  public Categoria insert(Categoria entity) throws EntityConstraintException {
    return repo.insert(entity);
  }

  @Override
  public void delete(Categoria entity) throws EntityNotFoundException {
    repo.delete(entity);
  }

  @Override
  public void deleteByID(Integer id) throws EntityNotFoundException {
    // Reassign plates that reference this category to the 'None' category (id=1)
    try {
      var none = repo.selectById(1);
      for (Plato p : platoService.selectAll()) {
        if (p.getCategoria() != null && p.getCategoria().getId().equals(id)) {
          p.setCategoria(none);
          platoService.update(p);
        }
      }
    } catch (EntityNotFoundException ignored) {
      // If none category doesn't exist, proceed without reassigning
    }

    repo.deleteByID(id);
  }

  @Override
  public Categoria update(Categoria entity) throws EntityConstraintException, EntityNotFoundException {
    return repo.update(entity);
  }

  @Override
  public Categoria upsert(Categoria entity) throws EntityConstraintException {
    return repo.upsert(entity);
  }
}
