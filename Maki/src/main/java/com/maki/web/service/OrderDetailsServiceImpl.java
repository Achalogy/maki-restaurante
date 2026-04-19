package com.maki.web.service;

import com.maki.web.entities.AdditionalOrderDetails;
import com.maki.web.entities.OrderDetails;
import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.repository.OrderDetailRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

  @Autowired
  private OrderDetailRepository repo;

  @Autowired
  private AdditionalOrderDetailsService additionalDetalleService;

  @Override
  public List<OrderDetails> selectAll() {
    return repo.findAll();
  }

  @Override
  public OrderDetails selectById(Long id) throws EntityNotFoundException {
    return repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Detalle de pedido no encontrado: " + id));
  }

  @Override
  public OrderDetails insert(OrderDetails entity) throws EntityConstraintException {
    if (entity.getId() != null) {
      throw new EntityConstraintException("El insert de PedidoDetalles no debe tener ID");
    }
    return repo.save(entity);
  }

  @Override
  public void delete(OrderDetails entity) throws EntityNotFoundException {
    deleteByID(entity.getId());
  }

  @Override
  @Transactional
  public void deleteByID(Long id) throws EntityNotFoundException {
    OrderDetails detalle = repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("No se puede eliminar: Detalle no encontrado"));

    // Limpiar los additionales asociados a este detalle específico antes de borrarlo
    for (AdditionalOrderDetails apd : additionalDetalleService.selectAll()) {
      if (apd.getDetail() != null && apd.getDetail().getId().equals(id)) {
        additionalDetalleService.deleteByID(apd.getId());
      }
    }

    repo.delete(detalle);
  }

  @Override
  public OrderDetails update(OrderDetails entity) throws EntityConstraintException, EntityNotFoundException {
    if (entity.getId() == null || !repo.existsById(entity.getId())) {
      throw new EntityNotFoundException("No se puede actualizar: Detalle de pedido no existe");
    }
    return repo.save(entity);
  }
}