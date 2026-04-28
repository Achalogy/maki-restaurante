import { Injectable } from '@angular/core';
import { PurchaseOrder } from "src/app/interfaces/purchase-order.interface";
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { PurchaseOrderDetails } from 'src/app/interfaces/order-details.interface';
import { Additional } from 'src/app/interfaces/additional.interface';

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
   * Obtiene los detalles de los productos de una orden específica.
   * @param orderId Identificador de la orden cuyos detalles se van a recuperar.
   * @returns Observable que emite un arreglo de PurchaseOrderDetails.
   */
  selectByOrderId(orderId: number): Observable<PurchaseOrderDetails[]> {
    return this.http.get<PurchaseOrderDetails[]>(
        `http://localhost:8080/api/v1/order-details/${orderId}`
    );
   }
  selectOrdersByClientId(clientId: number): Observable<PurchaseOrderDetails[]> {
    return this.http.get<PurchaseOrderDetails[]>(
        `http://localhost:8080/api/v1/order-details/client/${clientId}`
    );
  }
}
