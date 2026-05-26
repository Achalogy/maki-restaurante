package com.maki.web.service;

import com.maki.web.entities.OrderDetails;
import java.util.List;

/**
 * Interfaz de servicio para la lógica de negocio de OrderDetails. Extiende ServiceInterface para
 * operaciones CRUD estándar. Define métodos para encontrar detalles de pedido por ID de pedido o ID
 * de cliente.
 */
public interface OrderDetailsService extends ServiceInterface<OrderDetails> {

    /**
     * Encuentra todos los detalles de un pedido específico.
     *
     * @param orderId El ID del pedido para encontrar detalles
     * @return Lista de OrderDetails pertenecientes al pedido especificado
     */
    List<OrderDetails> findByOrderId(Long orderId);

    /**
     * Encuentra todos los detalles de todos los pedidos realizados por un cliente específico.
     *
     * @param clientId El ID del cliente para encontrar detalles de pedidos
     * @return Lista de todos los OrderDetails de todos los pedidos del cliente especificado
     */
    List<OrderDetails> findByClientId(Long clientId);
}
