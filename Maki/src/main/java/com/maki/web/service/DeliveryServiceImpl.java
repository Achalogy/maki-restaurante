package com.maki.web.service;

import com.maki.web.entities.Delivery;
import com.maki.web.entities.PurchaseOrder;
import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.repository.DeliveryRepository;
import com.maki.web.repository.PurchaseOrderRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryServiceImpl implements DeliveryService {

  @Autowired
  private DeliveryRepository repo;

  @Autowired
  private PurchaseOrderRepository purchaseOrderRepo;

  @Override
  public List<Delivery> selectAll() {
    return repo.findAll();
  }

  @Override
  public Delivery selectById(Long id) throws EntityNotFoundException {
    return repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Domiciliario no encontrado con ID: " + id));
  }

  @Override
  public Delivery insert(Delivery entity) throws EntityConstraintException {
    if (entity.getId() != null) {
      throw new EntityConstraintException("El insert de Domiciliario no debe tener ID");
    }
    return repo.save(entity);
  }

  @Override
  public void delete(Delivery entity) throws EntityNotFoundException {
    deleteByID(entity.getId());
  }

  @Override
  @Transactional
  public void deleteByID(Long id) throws EntityNotFoundException {
    Delivery domiciliario = repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Domiciliario no encontrado para eliminar: " + id));

    // Lógica de integridad: Desvincular pedidos antes de borrar al domiciliario
    for (PurchaseOrder p : purchaseOrderRepo.findAll()) {
      if (p.getDelivery() != null && p.getDelivery().getId().equals(id)) {
        p.setDelivery(null);
        purchaseOrderRepo.save(p);
      }
    }

    repo.delete(domiciliario);
  }

  @Override
  public Delivery update(Delivery entity) throws EntityConstraintException, EntityNotFoundException {
    if (entity.getId() == null || !repo.existsById(entity.getId())) {
      throw new EntityNotFoundException("No se puede actualizar: Domiciliario no encontrado");
    }
    return repo.save(entity);
  }

  @Override
  public List<Delivery> selectAllActive() {
    return repo.findAll().stream().filter(s -> s.isAvailable() && !s.isBusy()).collect(Collectors.toList());
  }
}
