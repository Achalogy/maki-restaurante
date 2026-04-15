package com.maki.web.service;

import com.maki.web.entities.AdicionalCategoria;
import com.maki.web.entities.Categoria;

import java.util.List;
import java.util.Locale.Category;

public interface AdicionalCategoriaService extends ServiceInterface<AdicionalCategoria> {
  List<AdicionalCategoria> findByCategory_Id(Long categoriaId);
  List<AdicionalCategoria> findByAditional_Id(Long adicionalId);

  List<AdicionalCategoria> setCategorias(Long aditionalId, List<Categoria> categories);
}
