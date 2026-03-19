package com.maki.web.service;

import com.maki.web.entities.AdicionalCategoria;
import java.util.List;

public interface AdicionalCategoriaService extends ServiceInterface<AdicionalCategoria> {
  List<AdicionalCategoria> findByCategoria_Id(Long categoriaId);
}
