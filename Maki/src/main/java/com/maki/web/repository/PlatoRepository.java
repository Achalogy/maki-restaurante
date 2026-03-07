package com.maki.web.repository;

import java.util.*;

import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.maki.web.entities.Categoria;
import com.maki.web.entities.Plato;

@Repository
public class PlatoRepository implements RepositoryInterface<Plato> {
  private Map<Long, Plato> platos = new HashMap<>();

  @Autowired
  private CategoriaRepository categoriasRepository;
  Categoria none;

  public PlatoRepository(CategoriaRepository categoriasRepository) {
    this.categoriasRepository = categoriasRepository;
    try {
      this.none = categoriasRepository.selectById(1L);
    } catch (EntityNotFoundException e) {
    }
  }

  @Override
  public Collection<Plato> selectAll() {
    return platos.values();
  }

  @Override
  public Plato selectById(Long id) throws EntityNotFoundException {
    Plato p = platos.get(id);

    if (p == null)
      throw new EntityNotFoundException("No hay plato con id=" + id);
    return p;
  }

  @Override
  public Plato insert(Plato plato) throws EntityConstraintException {
    Plato old = platos.get(plato.getId());
    if (old != null)
      throw new EntityConstraintException("Ya existe una categoría con este id");

    if (plato.getId() == null) {
      List<Long> ids = new ArrayList<>(platos.keySet());
      Long latestId = Collections.max(ids);
      plato.setId(latestId + 1);
    }

    platos.put(plato.getId(), plato);
    return plato;
  }

  @Override
  public void delete(Plato plato) throws EntityNotFoundException {
    if (platos.get(plato.getId()) == null)
      throw new EntityNotFoundException("No existe un plato con este id");

    platos.remove(plato.getId());
  }

  @Override
  public void deleteByID(Long id) throws EntityNotFoundException {
    if (platos.get(id) == null)
      throw new EntityNotFoundException("No existe un plato con id=" + id);

    platos.remove(id);
  }

  @Override
  public Plato update(Plato plato) throws EntityConstraintException, EntityNotFoundException {
    if (platos.get(plato.getId()) == null)
      throw new EntityNotFoundException("No existe un plato con este id");

    platos.put(plato.getId(), plato);
    return plato;
  }

  @Override
  public Plato upsert(Plato plato) throws EntityConstraintException {
    if (plato.getId() == null) {
      List<Long> ids = new ArrayList<>(platos.keySet());
      Long latestId = Collections.max(ids);
      plato.setId(latestId + 1);
    }

    platos.put(plato.getId(), plato);
    return plato;
  }
}
