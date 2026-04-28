import { Injectable } from '@angular/core';
import { PurchaseOrder } from "src/app/interfaces/purchase-order.interface";
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { PurchaseOrderDetails } from 'src/app/interfaces/order-details.interface';
import { Additional } from 'src/app/interfaces/additional.interface';

/**
 * Servicio para gestionar los datos de detalles de pedidos.
 * Se comunica con la API del backend para obtener artículos de pedidos por ID de pedido o ID de cliente.
 */
@Injectable({
  providedIn: 'root'
})
export class OrderDetailsService {

  /**
   * Inyecta HttpClient para poder realizar peticiones HTTP al backend.
   */
  constructor(
      private http: HttpClient
    ) { }

/**
   * Obtiene todos los artículos de detalles de un pedido específico.
   * @param orderId - El identificador único del pedido
   * @returns Observable con un array de PurchaseOrderDetails que contiene información del plato, cantidad y adicionales
   */
  selectByOrderId(orderId: number): Observable<PurchaseOrderDetails[]> {
    return this.http.get<PurchaseOrderDetails[]>(
        `http://localhost:8080/api/v1/order-details/${orderId}`
    );
  }

  /**
   * Obtiene todos los pedidos realizados por un cliente específico.
   * @param clientId - El identificador único del cliente
   * @returns Observable con un array de PurchaseOrderDetails que contiene todos los artículos del cliente
   */
  selectOrdersByClientId(clientId: number): Observable<PurchaseOrderDetails[]> {
    return this.http.get<PurchaseOrderDetails[]>(
        `http://localhost:8080/api/v1/order-details/client/${clientId}`
    );
  }
}
