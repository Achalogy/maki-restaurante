package com.maki.web.service;

import com.maki.web.entities.PurchaseOrder;
import com.maki.web.entities.Additional;
import com.maki.web.entities.AdditionalOrderDetails;
import com.maki.web.entities.OrderDetails;
import com.maki.web.entities.PlateWithAdditionals;
import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.repository.AdditionalOrderDetailsRepository;
import com.maki.web.repository.OrderDetailsRepository;
import com.maki.web.repository.PurchaseOrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

  @Autowired
  private PurchaseOrderRepository repo;

  @Autowired
  private OrderDetailsRepository orderDetailsRepo;
  
  @Autowired
  private ClientService clientService;

  @Autowired
  private AdditionalOrderDetailsRepository additionalOrderDetailsRepo;

  @Override
  public List<PurchaseOrder> selectAll() {
    return repo.findAll();
  }

  @Override
  public List<PurchaseOrder> selectNotCompleted() {
    return repo.findAll().stream().filter(x -> !x.getStatus().equals("completed")).collect(Collectors.toList());
  }

  @Override
  public PurchaseOrder selectById(Long id) throws EntityNotFoundException {
    return repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con ID: " + id));
  }

  @Override
  public PurchaseOrder insert(PurchaseOrder entity) throws EntityConstraintException {
    if (entity.getId() != null) {
      throw new EntityConstraintException("El insert de Pedido no debe incluir un ID");
    }
    // Opcional: Establecer fecha de creación por defecto si viene nula
    if (entity.getCreation_date() == null) {
      entity.setCreation_date(java.time.LocalDateTime.now());
    }
    return repo.save(entity);
  }

  @Override
  public void delete(PurchaseOrder entity) throws EntityNotFoundException {
    deleteByID(entity.getId());
  }

  @Override
  @Transactional
  public void deleteByID(Long id) throws EntityNotFoundException {
    PurchaseOrder pedido = repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("No se puede eliminar: Pedido no encontrado"));

    // Limpiar los detalles del pedido antes de borrar el pedido principal
    for (OrderDetails detalle : orderDetailsRepo.findAll()) {
      if (detalle.getOrder() != null && detalle.getOrder().getId().equals(id)) {
        orderDetailsRepo.deleteById(detalle.getId());
      }
    }

    repo.delete(pedido);
  }

  @Override
  public PurchaseOrder update(PurchaseOrder entity) throws EntityConstraintException, EntityNotFoundException {
    if (entity.getId() == null || !repo.existsById(entity.getId())) {
      throw new EntityNotFoundException("No se puede actualizar: Pedido no encontrado");
    }
    return repo.save(entity);
  }

  @Override
  public PurchaseOrder createPurchaseOrderFromcart(Long id, List<PlateWithAdditionals> plates) {
    PurchaseOrder order = this.insert(
      new PurchaseOrder(
        clientService.selectById(id)
      )
    );

    for (PlateWithAdditionals plate : plates) {
      OrderDetails detail = orderDetailsRepo.save(
        new OrderDetails(
          order,
          plate.detail.getPlate(),
          plate.detail.getQuantity())
        );

      for (Additional additional : plate.additionals) {
          additionalOrderDetailsRepo
            .save(
              new AdditionalOrderDetails(
                detail,
                additional)
            );
      }
    }

    return order;
  }
}