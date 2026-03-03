package com.maki.web.service;

import com.maki.web.entities.Categoria;
import com.maki.web.entities.Plato;
import com.maki.web.repository.exception.EntityNotFoundException;

public interface PlatoService extends ServiceInterface<Plato> {

  public void cambiarCategoria(Categoria categoria, int platoId) throws EntityNotFoundException;
}
