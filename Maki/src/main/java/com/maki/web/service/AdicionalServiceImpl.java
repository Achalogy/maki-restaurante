package com.maki.web.service;

import com.maki.web.entities.Aditional;
import com.maki.web.entities.AditionalCategory;
import com.maki.web.entities.AditionalOrderDetails;
import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.repository.AdicionalCategoriaRepository;
import com.maki.web.repository.AdicionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdicionalServiceImpl implements AdicionalService {

  @Autowired
  private AdicionalRepository repo;
  
  @Autowired
  private AdicionalCategoriaService adicionalCategoriaService;

  @Autowired
  private AdicionalPedidoDetallesService adicionalPedidoDetallesService;

  AdicionalServiceImpl() {
  }

  @Override
  public List<Aditional> selectAll() {
    return repo.findAll();
  }

  @Override
  public Aditional selectById(Long id) throws EntityNotFoundException {
    return repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Adicional no encontrado con ID: " + id));
  }

  @Override
  public Aditional insert(Aditional entity) throws EntityConstraintException {
    if (entity.getId() != null) {
      entity.setId(null);
      // throw new EntityConstraintException("El insert de Adicional no debe incluir un ID");
    }
    return repo.save(entity);
  }

  @Override
  public void delete(Aditional entity) throws EntityNotFoundException {
    deleteByID(entity.getId());
  }

  @Override
  public void deleteByID(Long id) throws EntityNotFoundException {
    if (!repo.existsById(id)) {
      throw new EntityNotFoundException("No se puede eliminar: Adicional no existe con ID: " + id);
    }

    for(AditionalCategory a: adicionalCategoriaService.selectAll()) {
      if(a.getAditional() != null && a.getAditional().getId() == id) {
        adicionalCategoriaService.delete(a);
      }
    }
    for(AditionalOrderDetails a: adicionalPedidoDetallesService.selectAll()) {
      if(a.getAditional() != null && a.getAditional().getId() == id) {
        adicionalPedidoDetallesService.delete(a);
      }
    }

    repo.deleteById(id);
  }

  @Override
  public Aditional update(Aditional entity) throws EntityConstraintException, EntityNotFoundException {
    if (entity.getId() == null || !repo.existsById(entity.getId())) {
      throw new EntityNotFoundException("No se puede actualizar: Adicional no encontrado");
    }
    return repo.save(entity);
  }
}