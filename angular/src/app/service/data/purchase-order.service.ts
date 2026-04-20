import { Injectable } from '@angular/core';
import { PurchaseOrder } from "src/app/interfaces/purchase-order.interface";
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

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
  //This 2 technically dont work yet, cause clients must create it, please finish this
  /*create(purchaseOrder: Partial<Omit<PurchaseOrder, 'id'>>): Observable<PurchaseOrder> {
    return this.http.post<PurchaseOrder>(`http://localhost:8080/api/v1/purchase-order`, purchaseOrder)
  }

  update(id: number, data: Partial<PurchaseOrder>): Observable<PurchaseOrder> {
    return this.http.post<PurchaseOrder>(`http://localhost:8080/api/v1/purchase-order/${id}`, data)
  }*/

  delete(id: number): Observable<Boolean> {
    return this.http.delete<Boolean>(`http://localhost:8080/api/v1/purchase-order/${id}`)
  }
}
