import { Injectable } from '@angular/core';
import { PurchaseOrder } from "src/app/interfaces/purchase-order.interface";
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { PurchaseOrderDetails } from 'src/app/interfaces/order-details.interface';
import { Additional } from 'src/app/interfaces/additional.interface';

@Injectable({
  providedIn: 'root'
})
export class PurchaseOrderService {

      constructor(
      private http: HttpClient
    ) { }

  /** Obtiene todas las órdenes de compra. */
  selectAll(): Observable<PurchaseOrder[]> {
    return this.http.get<PurchaseOrder[]>('http://localhost:8080/api/v1/purchase-order')
  }

  /** Obtiene todas las órdenes de activas. */
  selectNotCompleted(): Observable<PurchaseOrder[]> {
    return this.http.get<PurchaseOrder[]>('http://localhost:8080/api/v1/purchase-order?notCompleted=1')
  }

  /**
   * Obtiene una orden de compra por su ID.
   * @param id El ID de la orden de compra.
   * @returns Un observable que emite la orden de compra correspondiente.
   */
  selectById(id: number): Observable<PurchaseOrder> {
    return this.http.get<PurchaseOrder>(`http://localhost:8080/api/v1/purchase-order/${id}`)
  }
  
  /**
   * Crea una nueva orden de compra para un cliente con los platos especificados.
   * @param clientId El ID del cliente para el cual se crea la orden.
   * @param plates Un arreglo de detalles de platos con sus adicionales.
   * @returns Un observable que emite la orden de compra creada.
   */
  create(
    clientId: number,
    plates: (PurchaseOrderDetails & {
        aditionals: Additional[]
      })[]
  ): Observable<PurchaseOrder> {

    const payload = plates.map(p => ({
      detail: {
        id: -1,
        order: {},
        plate: p.plate,
        quantity: p.quantity
      },
      additionals: p.aditionals
    }))

    return this.http.post<PurchaseOrder>(`http://localhost:8080/api/v1/purchase-order/client/${clientId}`, payload)
  }

  update(id: number, data: Partial<PurchaseOrder>): Observable<PurchaseOrder> {
    return this.http.post<PurchaseOrder>(`http://localhost:8080/api/v1/purchase-order/${id}`, data)
  }

  /**
   * Elimina una orden de compra por su ID.
   * @param id El ID de la orden de compra a eliminar.
   * @returns Un observable que emite un booleano indicando si la eliminación fue exitosa.
   */
  delete(id: number): Observable<Boolean> {
    return this.http.delete<Boolean>(`http://localhost:8080/api/v1/purchase-order/${id}`)
  }
}
