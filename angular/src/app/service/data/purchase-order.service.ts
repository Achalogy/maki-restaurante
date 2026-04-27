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

  selectAll(): Observable<PurchaseOrder[]> {
    return this.http.get<PurchaseOrder[]>('http://localhost:8080/api/v1/purchase-order')
  }

  selectById(id: number): Observable<PurchaseOrder> {
    return this.http.get<PurchaseOrder>(`http://localhost:8080/api/v1/purchase-order/${id}`)
  }
  
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

  delete(id: number): Observable<Boolean> {
    return this.http.delete<Boolean>(`http://localhost:8080/api/v1/purchase-order/${id}`)
  }
}
