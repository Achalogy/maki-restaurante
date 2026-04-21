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

  constructor(
      private http: HttpClient
    ) { }

    selectByOrderId(orderId: number): Observable<PurchaseOrderDetails[]> {
    return this.http.get<PurchaseOrderDetails[]>(
        `http://localhost:8080/api/v1/order-details/${orderId}`
    );
    }
}
