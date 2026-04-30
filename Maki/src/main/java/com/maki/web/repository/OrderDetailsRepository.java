package com.maki.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.maki.web.entities.OrderDetails;

/**
 * Interfaz de repositorio para la entidad OrderDetails.
 * Proporciona métodos de consulta a la base de datos usando consultas derivadas de Spring Data JPA.
 */
@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {

    /**
     * Encuentra todos los detalles de pedido asociados a un pedido específico.
     * @param orderId El ID del pedido para encontrar detalles
     * @return Lista de OrderDetails pertenecientes al pedido especificado
     */
    List<OrderDetails> findByOrder_Id(Long orderId);

    /**
     * Encuentra todos los detalles de pedido de los pedidos realizados por un cliente específico.
     * Navega a través de la relación: OrderDetails -> Order -> Client -> id
     * @param clientId El ID del cliente para encontrar detalles de pedidos
     * @return Lista de todos los OrderDetails de todos los pedidos del cliente especificado
     */
    List<OrderDetails> findByOrder_Client_Id(Long clientId);

}
