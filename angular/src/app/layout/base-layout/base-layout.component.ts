import { Component, EventEmitter, Output } from '@angular/core';
import { ShoppingCartService } from 'src/app/service/ui/shopping-card.service';

@Component({
  selector: 'app-base-layout',
  templateUrl: './base-layout.component.html',
  styleUrls: ['./base-layout.component.css']
})
export class BaseLayoutComponent {

  constructor(
    public cart: ShoppingCartService
  ) {

  }

  openCart() {
    this.cart.open()
  }

  closeCart() {
    this.cart.open()
  }

}
