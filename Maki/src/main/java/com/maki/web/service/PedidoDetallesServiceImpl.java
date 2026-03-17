package com.maki.web.service;

import com.maki.web.entities.AdicionalPedidoDetalles;
import com.maki.web.entities.PedidoDetalles;
import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.repository.PedidoDetallesRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PedidoDetallesServiceImpl implements PedidoDetallesService {

  @Autowired
  private PedidoDetallesRepository repo;

  @Autowired
  private AdicionalPedidoDetallesService adicionalDetalleService;

  @Override
  public Collection<PedidoDetalles> selectAll() {
    return repo.findAll();
  }

  @Override
  public PedidoDetalles selectById(Long id) throws EntityNotFoundException {
    return repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Detalle de pedido no encontrado: " + id));
  }

  @Override
  public PedidoDetalles insert(PedidoDetalles entity) throws EntityConstraintException {
    if (entity.getId() != null) {
      throw new EntityConstraintException("El insert de PedidoDetalles no debe tener ID");
    }
    return repo.save(entity);
  }

  @Override
  public void delete(PedidoDetalles entity) throws EntityNotFoundException {
    deleteByID(entity.getId());
  }

  @Override
  @Transactional
  public void deleteByID(Long id) throws EntityNotFoundException {
    PedidoDetalles detalle = repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("No se puede eliminar: Detalle no encontrado"));

    // Limpiar los adicionales asociados a este detalle específico antes de borrarlo
    for (AdicionalPedidoDetalles apd : adicionalDetalleService.selectAll()) {
      if (apd.getDetalle() != null && apd.getDetalle().getId().equals(id)) {
        adicionalDetalleService.deleteByID(apd.getId());
      }
    }

    repo.delete(detalle);
  }

  @Override
  public PedidoDetalles update(PedidoDetalles entity) throws EntityConstraintException, EntityNotFoundException {
    if (entity.getId() == null || !repo.existsById(entity.getId())) {
      throw new EntityNotFoundException("No se puede actualizar: Detalle de pedido no existe");
    }
    return repo.save(entity);
  }
}