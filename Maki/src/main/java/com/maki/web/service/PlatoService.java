package com.maki.web.service;

import com.maki.web.entities.Categoria;
import com.maki.web.entities.Plato;
import com.maki.web.exception.EntityNotFoundException;

public interface PlatoService extends ServiceInterface<Plato> {

  public void cambiarCategoria(Categoria categoria, Long platoId) throws EntityNotFoundException;
}
