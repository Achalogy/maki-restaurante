import { Component, EventEmitter, Output } from '@angular/core';
import { PurchaseOrderDetails } from 'src/app/interfaces/order-details.interface';
import { Plate } from 'src/app/interfaces/plate.interface';
import { ShoppingCartService } from 'src/app/service/ui/shopping-card.service';

@Component({
  selector: 'app-base-layout',
  templateUrl: './base-layout.component.html',
  styleUrls: ['./base-layout.component.css']
})
export class BaseLayoutComponent {

  shoppingCart: PurchaseOrderDetails[] = []

  constructor(
    public cart: ShoppingCartService
  ) {
    this.shoppingCart = cart.getShoppingCart()
  }

  getPrice(): number {
    return this.shoppingCart.reduce((acc, curr) => {
      return acc + (curr.quantity * curr.plate.price)
    }, 0)
  }

  openCart() {
    this.cart.open()
  }

  closeCart() {
    this.cart.open()
  }

  removeItem(plate: Plate) {
    this.cart.removeItem(plate)
    this.shoppingCart = this.cart.getShoppingCart()
  }
}
