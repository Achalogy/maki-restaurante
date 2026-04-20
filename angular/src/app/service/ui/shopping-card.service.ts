import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { PurchaseOrderDetails } from 'src/app/interfaces/order-details.interface';
import { Plate } from 'src/app/interfaces/plate.interface';
import { PurchaseOrder } from 'src/app/interfaces/purchase-order.interface';

@Injectable({
  providedIn: 'root'
})
export class ShoppingCartService {
  // Emite eventos :D
  private isOpen$ = new BehaviorSubject<boolean>(false);
  private shoppingCart: PurchaseOrderDetails[] = []

  toggle() {
    this.isOpen$.next(!this.isOpen$.value);
  }

  open() {
    this.isOpen$.next(true);
  }

  close() {
    this.isOpen$.next(false);
  }

  get state() {
    return this.isOpen$.asObservable();
  }

  getShoppingCart() {
    return this.shoppingCart
  }

  addItem(plate: Plate) {
    
    const oldItem = this.shoppingCart.findIndex(d => d.plate.id == plate.id)

    if(oldItem != -1)
      this.shoppingCart[oldItem]
        .quantity++;
    else this.shoppingCart.push(
      {
        plate,
        quantity: 1,
        order: {} as any,
        id: -1
      }
    )
  }
  removeItem(plate: Plate) {
    this.shoppingCart = this.shoppingCart.filter(x => x.plate.id != plate.id)
  }

}