package com.maki.web.service;

import com.maki.web.entities.Categoria;
import com.maki.web.repository.CategoriaRepository;
import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

public class CategoriaServiceImpl implements CategoriaService {


  @Autowired
  CategoriaRepository repo;

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
