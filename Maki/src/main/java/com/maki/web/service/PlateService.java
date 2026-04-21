package com.maki.web.service;

import com.maki.web.entities.Category;
import com.maki.web.entities.Plate;
import com.maki.web.exception.EntityNotFoundException;

public interface PlateService extends ServiceInterface<Plate> {

  public void cambiarCategoria(Category categoria, Long platoId) throws EntityNotFoundException;
}
