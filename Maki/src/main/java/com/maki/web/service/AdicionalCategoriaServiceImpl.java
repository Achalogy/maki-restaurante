package com.maki.web.service;

import com.maki.web.entities.AditionalCategory;
import com.maki.web.entities.Category;
import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.repository.AdicionalCategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdicionalCategoriaServiceImpl implements AdicionalCategoriaService {

  @Autowired
  private AdicionalCategoriaRepository repo;

  @Override
  public List<AditionalCategory> selectAll() {
    return repo.findAll();
  }

  @Override
  public AditionalCategory selectById(Long id) throws EntityNotFoundException {
    return repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Relación Adicional-Categoría no encontrada: " + id));
  }

  @Override
  public AditionalCategory insert(AditionalCategory entity) throws EntityConstraintException {
    if (entity.getId() != null) {
      throw new EntityConstraintException("El insert de AdicionalCategoria no debe tener ID");
    }
    return repo.save(entity);
  }

  @Override
  public void delete(AditionalCategory entity) throws EntityNotFoundException {
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
  public AditionalCategory update(AditionalCategory entity)
      throws EntityConstraintException, EntityNotFoundException {
    if (entity.getId() == null || !repo.existsById(entity.getId())) {
      throw new EntityNotFoundException("No se puede actualizar: El registro no existe");
    }
    return repo.save(entity);
  }

  @Override
  public List<AditionalCategory> findByCategory_Id(Long categoriaId) {
    return repo.findByCategory_Id(categoriaId);
  }

  @Override
  public List<AditionalCategory> findByAditional_Id(Long adicionalId) {
    return repo.findByAditional_Id(adicionalId);
  }

  @Override
  public List<AditionalCategory> setCategorias(Long aditionalId, List<Category> categories) {
    List<AditionalCategory> payload = new ArrayList<>();

    for(AditionalCategory a: repo.findByAditional_Id(aditionalId)) {
      repo.delete(a);
    }
    for(Category c: categories) {
      AditionalCategory adc = new AditionalCategory(
          c.getId(),
          aditionalId
        );
      repo.save(
        adc
      );
      payload.add(adc);
    }

    return payload;
  }
}