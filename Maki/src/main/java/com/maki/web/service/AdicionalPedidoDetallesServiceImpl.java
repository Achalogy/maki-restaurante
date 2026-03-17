package com.maki.web.service;

import com.maki.web.entities.AdicionalPedidoDetalles;
import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.repository.AdicionalPedidoDetallesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AdicionalPedidoDetallesServiceImpl implements AdicionalPedidoDetallesService {

  @Autowired
  private AdicionalPedidoDetallesRepository repo;

  @Override
  public Collection<AdicionalPedidoDetalles> selectAll() {
    return repo.findAll();
  }

  @Override
  public AdicionalPedidoDetalles selectById(Long id) throws EntityNotFoundException {
    return repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Registro de AdicionalPedidoDetalles no encontrado: " + id));
  }

  @Override
  public AdicionalPedidoDetalles insert(AdicionalPedidoDetalles entity) throws EntityConstraintException {
    if (entity.getId() != null) {
      throw new EntityConstraintException("El insert de AdicionalPedidoDetalles no debe tener ID");
    }
    return repo.save(entity);
  }

  @Override
  public void delete(AdicionalPedidoDetalles entity) throws EntityNotFoundException {
    deleteByID(entity.getId());
  }

  @Override
  public void deleteByID(Long id) throws EntityNotFoundException {
    if (!repo.existsById(id)) {
      throw new EntityNotFoundException("No se puede eliminar, ID no encontrado: " + id);
    }
    repo.deleteById(id);
  }

  @Override
  public AdicionalPedidoDetalles update(AdicionalPedidoDetalles entity)
      throws EntityConstraintException, EntityNotFoundException {
    if (entity.getId() == null || !repo.existsById(entity.getId())) {
      throw new EntityNotFoundException("No se puede actualizar, el registro no existe");
    }
    return repo.save(entity);
  }
}