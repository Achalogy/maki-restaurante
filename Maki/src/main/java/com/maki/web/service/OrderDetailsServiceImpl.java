package com.maki.web.service;

import com.maki.web.entities.AdditionalOrderDetails;
import com.maki.web.entities.OrderDetails;
import com.maki.web.exception.EntityConstraintException;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.repository.AdditionalOrderDetailsRepository;
import com.maki.web.repository.OrderDetailsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación de OrderDetailsService.
 * Maneja la lógica de negocio para detalles de pedidos incluyendo operaciones CRUD
 * y consultas personalizadas. Gestiona la limpieza de AdditionalOrderDetails relacionados al eliminar.
 */
@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

  @Autowired
  private OrderDetailsRepository repo;

  @Autowired
  private AdditionalOrderDetailsRepository additionalOrderDetailsRepo;

  /**
   * Obtiene todos los detalles de pedidos de la base de datos.
   * @return Lista de todas las entidades OrderDetails
   */
  @Override
  public List<OrderDetails> selectAll() {
    return repo.findAll();
  }

  /**
   * Encuentra un solo detalle de pedido por su ID.
   * @param id El ID del detalle de pedido a encontrar
   * @return La entidad OrderDetails
   * @throws EntityNotFoundException si no se encuentra ningún detalle de pedido con el ID dado
   */
  @Override
  public OrderDetails selectById(Long id) throws EntityNotFoundException {
    return repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Detalle de pedido no encontrado: " + id));
  }

  /**
   * Crea un nuevo detalle de pedido.
   * @param entity La entidad OrderDetails a crear
   * @return El OrderDetails creado con ID generado
   * @throws EntityConstraintException si la entidad tiene un ID (debería ser null para nuevas entidades)
   */
  @Override
  public OrderDetails insert(OrderDetails entity) throws EntityConstraintException {
    if (entity.getId() != null) {
      throw new EntityConstraintException("El insert de PedidoDetalles no debe tener ID");
    }
    return repo.save(entity);
  }

  /**
   * Elimina una entidad de detalle de pedido.
   * @param entity La entidad OrderDetails a eliminar
   * @throws EntityNotFoundException si la entidad no se encuentra
   */
  @Override
  public void delete(OrderDetails entity) throws EntityNotFoundException {
    deleteByID(entity.getId());
  }

  /**
   * Elimina un detalle de pedido por su ID.
   * Primero elimina todos los AdditionalOrderDetails asociados antes de eliminar la entidad principal.
   * @param id El ID del detalle de pedido a eliminar
   * @throws EntityNotFoundException si no se encuentra ningún detalle de pedido con el ID dado
   */
  @Override
  @Transactional
  public void deleteByID(Long id) throws EntityNotFoundException {
    OrderDetails detalle = repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("No se puede eliminar: Detalle no encontrado"));

    // Limpiar los additionales asociados a este detalle específico antes de borrarlo
    for (AdditionalOrderDetails apd : additionalOrderDetailsRepo.findAll()) {
      if (apd.getDetail() != null && apd.getDetail().getId().equals(id)) {
        additionalOrderDetailsRepo.deleteById(apd.getId());
      }
    }

    repo.delete(detalle);
  }

  /**
   * Actualiza un detalle de pedido existente.
   * @param entity La entidad OrderDetails con datos actualizados
   * @return La entidad OrderDetails actualizada
   * @throws EntityConstraintException si el ID de la entidad es null
   * @throws EntityNotFoundException si no existe ningún detalle de pedido con el ID dado
   */
  @Override
  public OrderDetails update(OrderDetails entity) throws EntityConstraintException, EntityNotFoundException {
    if (entity.getId() == null || !repo.existsById(entity.getId())) {
      throw new EntityNotFoundException("No se puede actualizar: Detalle de pedido no existe");
    }
    return repo.save(entity);
  }

  /**
   * Encuentra todos los detalles de un pedido específico.
   * @param orderId El ID del pedido para encontrar detalles
   * @return Lista de OrderDetails pertenecientes al pedido especificado
   */
  @Override
  public List<OrderDetails> findByOrderId(Long orderId) {
    return repo.findByOrder_Id(orderId);
  }

  /**
   * Encuentra todos los detalles de todos los pedidos realizados por un cliente específico.
   * @param clientId El ID del cliente para encontrar detalles de pedidos
   * @return Lista de todos los OrderDetails de todos los pedidos del cliente especificado
   */
  @Override
  public List<OrderDetails> findByClientId(Long clientId) {
    return repo.findByOrder_Client_Id(clientId);
  }
}
