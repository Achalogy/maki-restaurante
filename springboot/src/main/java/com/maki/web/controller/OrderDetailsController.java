package com.maki.web.controller;

import com.maki.web.entities.OrderDetails;
import com.maki.web.service.OrderDetailsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para endpoints de Detalles de Pedidos. Proporciona endpoints API para obtener
 * artículos de detalles de pedidos por ID de pedido o ID de cliente. Ruta base:
 * /api/v1/order-details
 */
@RestController
@RequestMapping("/api/v1/order-details")
public class OrderDetailsController {

    @Autowired private OrderDetailsService orderDetailsService;

    /**
     * Obtiene todos los artículos de detalles de un pedido específico.
     *
     * @param id El ID del pedido (Long) para obtener los detalles
     * @return ResponseEntity conteniendo lista de OrderDetails y HTTP 200 OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<List<OrderDetails>> getOrderDetailsById(@PathVariable Long id) {
        List<OrderDetails> orderDetails = orderDetailsService.findByOrderId(id);
        return new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }

    /**
     * Obtiene todos los artículos de detalles de todos los pedidos realizados por un cliente
     * específico.
     *
     * @param clientId El ID del cliente (Long) para obtener los pedidos
     * @return ResponseEntity conteniendo lista de OrderDetails y HTTP 200 OK
     */
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<OrderDetails>> getOrderDetailsByClientId(
            @PathVariable Long clientId) {
        List<OrderDetails> orderDetails = orderDetailsService.findByClientId(clientId);
        return new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }
}
