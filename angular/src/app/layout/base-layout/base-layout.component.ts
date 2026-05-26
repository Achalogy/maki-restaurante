import { Component } from "@angular/core";
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

  // Propiedades de la pasarela de pago simulada
  showPaymentGateway = false;
  paymentCardName = "";
  paymentCardNumber = "";
  paymentExpiry = "";
  paymentCvv = "";

  isPaying = false;
  paymentSuccess = false;
  errorMessage: string | null = null;

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

  // Abrir modal de pasarela de pago simulada
  openPaymentGateway() {
    if (this.shoppingCart.length === 0) {
      alert("Tu carrito está vacío.");
      return;
    }
    this.showPaymentGateway = true;
    this.paymentCardName = "";
    this.paymentCardNumber = "";
    this.paymentExpiry = "";
    this.paymentCvv = "";
    this.isPaying = false;
    this.paymentSuccess = false;
    this.errorMessage = null;
  }

  // Cerrar modal de pasarela de pago
  closePaymentGateway() {
    this.showPaymentGateway = false;
  }

  // Formateadores automáticos de inputs para tarjeta
  onCardNumberInput(event: any) {
    let input = event.target.value.replace(/\D/g, ""); // Solo dígitos
    if (input.length > 16) {
      input = input.substring(0, 16);
    }
    // Agrupar de a 4 dígitos
    const formatted = input.match(/.{1,4}/g)?.join(" ") || "";
    this.paymentCardNumber = formatted;
  }

  onExpiryInput(event: any) {
    let input = event.target.value.replace(/\D/g, ""); // Solo dígitos
    if (input.length > 4) {
      input = input.substring(0, 4);
    }
    if (input.length > 2) {
      this.paymentExpiry = input.substring(0, 2) + "/" + input.substring(2);
    } else {
      this.paymentExpiry = input;
    }
  }

  onCvvInput(event: any) {
    let input = event.target.value.replace(/\D/g, ""); // Solo dígitos
    if (input.length > 4) {
      input = input.substring(0, 4);
    }
    this.paymentCvv = input;
  }

  // Procesar el pago simulado
  processPayment() {
    if (!this.paymentCardName.trim()) {
      this.errorMessage = "Por favor ingresa el nombre del titular.";
      return;
    }

    // Quitar espacios para validar número de tarjeta
    const cleanCardNumber = this.paymentCardNumber.replace(/\s+/g, "");
    if (cleanCardNumber.length !== 16 || isNaN(Number(cleanCardNumber))) {
      this.errorMessage = "El número de tarjeta debe tener 16 dígitos.";
      return;
    }

    // Validar fecha de expiración MM/YY
    const expiryRegex = /^(0[1-9]|1[0-2])\/([0-9]{2})$/;
    if (!expiryRegex.test(this.paymentExpiry)) {
      this.errorMessage =
        "La fecha de vencimiento debe estar en formato MM/YY.";
      return;
    }

    // Validar CVV
    if (
      this.paymentCvv.length < 3 ||
      this.paymentCvv.length > 4 ||
      isNaN(Number(this.paymentCvv))
    ) {
      this.errorMessage =
        "El código de seguridad (CVV) debe tener 3 o 4 dígitos.";
      return;
    }

    this.errorMessage = null;
    this.isPaying = true;

    // Simulación de respuesta de la pasarela tras 2 segundos
    setTimeout(() => {
      this.purchaseOrderService.create(this.cart.getShoppingCart()).subscribe({
        next: () => {
          this.isPaying = false;
          this.paymentSuccess = true;
          this.cart.clear();
          this.shoppingCart = this.cart.getShoppingCart();

          // Cerrar automáticamente la pasarela y el carrito después del éxito
          setTimeout(() => {
            this.closePaymentGateway();
            this.cart.close();
          }, 2500);
        },
        error: (err) => {
          console.log(err);
          this.isPaying = false;
          this.errorMessage =
            "Error al procesar el pedido con el servidor. Por favor, inténtelo de nuevo.";
        },
      });
    }, 2000);
  }

  createOrder() {
    this.openPaymentGateway();
  }
}
