package com.maki.web.service;

import com.maki.web.entities.AditionalCategory;
import com.maki.web.entities.Category;

import java.util.List;

public interface AdicionalCategoriaService extends ServiceInterface<AditionalCategory> {
  List<AditionalCategory> findByCategory_Id(Long categoriaId);
  List<AditionalCategory> findByAditional_Id(Long adicionalId);

  List<AditionalCategory> setCategorias(Long aditionalId, List<Category> categories);
}
