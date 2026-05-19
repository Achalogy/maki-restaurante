import { Component, EventEmitter, Output } from "@angular/core";
import { Additional } from "src/app/interfaces/additional.interface";
import { PurchaseOrderDetails } from "src/app/interfaces/order-details.interface";
import { Plate } from "src/app/interfaces/plate.interface";
import { PurchaseOrderService } from "src/app/service/data/purchase-order.service";
import { ShoppingCartService } from "src/app/service/ui/shopping-card.service";

@Component({
  selector: "app-base-layout",
  templateUrl: "./base-layout.component.html",
  styleUrls: ["./base-layout.component.css"],
})
export class BaseLayoutComponent {
  shoppingCart: (PurchaseOrderDetails & {
    aditionals: Additional[];
  })[] = [];

  constructor(
    public cart: ShoppingCartService,
    private purchaseOrderService: PurchaseOrderService,
  ) {
    this.shoppingCart = cart.getShoppingCart();
  }

  getPrice(): number {
    return this.shoppingCart.reduce((acc, curr) => {
      return (
        acc +
        curr.quantity *
          (curr.plate.price +
            curr.aditionals.reduce((acc, curr) => acc + curr.price, 0))
      );
    }, 0);
  }

  getItemPrice(
    item: PurchaseOrderDetails & {
      aditionals: Additional[];
    },
  ) {
    return (
      (item.plate.price +
        item.aditionals.reduceRight((acc, curr) => acc + curr.price, 0)) *
      item.quantity
    );
  }

  openCart() {
    this.cart.open();
  }

  closeCart() {
    this.cart.open();
  }

  removeItem(plate: Plate) {
    this.cart.removeItem(plate);
    this.shoppingCart = this.cart.getShoppingCart();
  }

  createOrder() {
    const clientId = localStorage.getItem("user_id")!;

    this.purchaseOrderService
      .create(+clientId, this.cart.getShoppingCart())
      .subscribe(() => {
        alert("Pedido creado!");
        this.cart.clear();
        this.shoppingCart = this.cart.getShoppingCart();
      });
  }
}
