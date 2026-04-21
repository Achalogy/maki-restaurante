package com.maki.web.service;

import com.maki.web.entities.AdditionalCategory;
import com.maki.web.entities.Category;
import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.repository.AdditionalCategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdditionalcategoryServiceImpl implements AdditionalCategoryService {

  @Autowired
  private AdditionalCategoryRepository repo;

  @Override
  public List<AdditionalCategory> selectAll() {
    return repo.findAll();
  }

  @Override
  public AdditionalCategory selectById(Long id) throws EntityNotFoundException {
    return repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Relación Adicional-Categoría no encontrada: " + id));
  }

  @Override
  public AdditionalCategory insert(AdditionalCategory entity) throws EntityConstraintException {
    if (entity.getId() != null) {
      throw new EntityConstraintException("El insert de AdicionalCategoria no debe tener ID");
    }
    return repo.save(entity);
  }

  @Override
  public void delete(AdditionalCategory entity) throws EntityNotFoundException {
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
  public AdditionalCategory update(AdditionalCategory entity)
      throws EntityConstraintException, EntityNotFoundException {
    if (entity.getId() == null || !repo.existsById(entity.getId())) {
      throw new EntityNotFoundException("No se puede actualizar: El registro no existe");
    }
    return repo.save(entity);
  }

  @Override
  public List<AdditionalCategory> findByCategory_Id(Long categoriaId) {
    return repo.findByCategory_Id(categoriaId);
  }

  @Override
  public List<AdditionalCategory> findByAdditional_Id(Long additionalId) {
    return repo.findByAdditional_Id(additionalId);
  }

  @Override
  public List<AdditionalCategory> setCategories(Long additional_id, List<Category> categories) {
    List<AdditionalCategory> payload = new ArrayList<>();

    for(AdditionalCategory a: repo.findByAdditional_Id(additional_id)) {
      repo.delete(a);
    }
    for(Category c: categories) {
      AdditionalCategory adc = new AdditionalCategory(
          c.getId(),
          additional_id
        );
      repo.save(
        adc
      );
      payload.add(adc);
    }

    return payload;
  }
}