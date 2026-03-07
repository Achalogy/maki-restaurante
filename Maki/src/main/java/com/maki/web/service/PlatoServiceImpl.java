package com.maki.web.service;

import com.maki.web.entities.Categoria;
import com.maki.web.entities.Plato;
import com.maki.web.repository.PlatoRepository;
import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PlatoServiceImpl implements PlatoService {

  @Autowired
  PlatoRepository repo;

  @Override
  public Collection<Plato> selectAll() {
    return repo.selectAll();
  }

  @Override
  public Plato selectById(Long id) throws EntityNotFoundException {
    return repo.selectById(id);
  }

  @Override
  public Plato insert(Plato entity) throws EntityConstraintException {
    return repo.insert(entity);
  }

  @Override
  public void delete(Plato entity) throws EntityNotFoundException {
    repo.delete(entity);
  }

  @Override
  public void deleteByID(Long id) throws EntityNotFoundException {
    repo.deleteByID(id);
  }

  @Override
  public Plato update(Plato entity) throws EntityConstraintException, EntityNotFoundException {
    return repo.update(entity);
  }

  @Override
  public Plato upsert(Plato entity) throws EntityConstraintException {
    return repo.upsert(entity);
  }

  @Override
  public void cambiarCategoria(Categoria categoria, Long platoId) throws EntityNotFoundException {
    Plato plato = this.selectById(platoId);

    plato.setCategoria(categoria);

    this.update(plato);
  }
}
