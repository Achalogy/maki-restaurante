package com.maki.web.service;

import com.maki.web.entities.Pedido;
import com.maki.web.entities.PedidoDetalles;
import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PedidoServiceImpl implements PedidoService {

  @Autowired
  private PedidoRepository repo;

  @Autowired
  private PedidoDetallesService pedidoDetallesService;

  @Override
  public Collection<Pedido> selectAll() {
    return repo.findAll();
  }

  @Override
  public Pedido selectById(Long id) throws EntityNotFoundException {
    return repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con ID: " + id));
  }

  @Override
  public Pedido insert(Pedido entity) throws EntityConstraintException {
    if (entity.getId() != null) {
      throw new EntityConstraintException("El insert de Pedido no debe incluir un ID");
    }
    // Opcional: Establecer fecha de creación por defecto si viene nula
    if (entity.getFechaCreacion() == null) {
      entity.setFechaCreacion(java.time.LocalDateTime.now());
    }
    return repo.save(entity);
  }

  @Override
  public void delete(Pedido entity) throws EntityNotFoundException {
    deleteByID(entity.getId());
  }

  @Override
  @Transactional
  public void deleteByID(Long id) throws EntityNotFoundException {
    Pedido pedido = repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("No se puede eliminar: Pedido no encontrado"));

    // Limpiar los detalles del pedido antes de borrar el pedido principal
    for (PedidoDetalles detalle : pedidoDetallesService.selectAll()) {
      if (detalle.getPedido() != null && detalle.getPedido().getId().equals(id)) {
        pedidoDetallesService.deleteByID(detalle.getId());
      }
    }

    repo.delete(pedido);
  }

  @Override
  public Pedido update(Pedido entity) throws EntityConstraintException, EntityNotFoundException {
    if (entity.getId() == null || !repo.existsById(entity.getId())) {
      throw new EntityNotFoundException("No se puede actualizar: Pedido no encontrado");
    }
    return repo.save(entity);
  }
}