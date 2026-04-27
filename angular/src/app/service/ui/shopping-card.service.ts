import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Additional } from 'src/app/interfaces/additional.interface';
import { PurchaseOrderDetails } from 'src/app/interfaces/order-details.interface';
import { Plate } from 'src/app/interfaces/plate.interface';

@Injectable({
  providedIn: 'root'
})
export class ShoppingCartService {
  // BehaviorSubject que emite cambios en el estado de apertura/cierre del carrito.
  private isOpen$ = new BehaviorSubject<boolean>(false);
  // Array interno que almacena los detalles de los pedidos y sus adicionales.
  private shoppingCart: (PurchaseOrderDetails & {
    aditionals: Additional[]
  })[] = []

  /**
   * Guarda el carrito actual en el localStorage del navegador.
   * Se serializa como JSON para mantener el estado entre recargas.
   */
  persistCart() {
    window.localStorage.setItem("cart", JSON.stringify(this.shoppingCart))
  }

  // Alterna entre abrir y cerrar el carrito.
  toggle() {
    this.isOpen$.next(!this.isOpen$.value);
  }

  // Abre el carrito.
  open() {
    this.isOpen$.next(true);
  }

  // Cierra el carrito.
  close() {
    this.isOpen$.next(false);
  }

  // Devuelve un observable para suscribirse al estado abierto/cerrado del carrito.
  get state() {
    return this.isOpen$.asObservable();
  }

  // Recupera el carrito de compras del localStorage y lo devuelve.
  getShoppingCart() {
    this.shoppingCart = JSON.parse(window.localStorage.getItem("cart") ?? "[]")
    return this.shoppingCart
  }

  // Agrega un plato al carrito. Si ya existe un ítem igual con los mismos adicionales,
  // incrementa su cantidad, de lo contrario agrega un nuevo registro.
  addItem(plate: Plate, additionals: Additional[] = []) {
    
    const oldItem = this.shoppingCart.findIndex(d => d.plate.id == plate.id && d.aditionals.length == additionals.length && d.aditionals.every(x => additionals.includes(x)))

    if(oldItem != -1)
      this.shoppingCart[oldItem]
        .quantity++;
    else this.shoppingCart.push(
      {
        plate,
        quantity: 1,
        order: {} as any,
        id: -1,
        aditionals: additionals
      }
    )

    this.persistCart()
  }

  // Elimina todos los elementos del carrito que correspondan al plato dado.
  removeItem(plate: Plate) {
    this.shoppingCart = this.shoppingCart.filter(x => x.plate.id != plate.id)
    this.persistCart()
  }
  
  // Vacía completamente el carrito y persiste el cambio.
  clear() {
    this.shoppingCart = []
    this.persistCart()
  }
}