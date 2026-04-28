import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PurchaseOrderDetails } from 'src/app/interfaces/order-details.interface';
import { OrderDetailsService } from 'src/app/service/data/order-details.service';


/**
 * Componente de Pedidos de Cliente
 * Maneja la visualización y gestión de los pedidos del cliente incluyendo:
 * - Lista de pedidos con detalles agrupados
 */
@Component({
  selector: 'app-client-orders',
  templateUrl: './client-orders.component.html',
  styleUrls: ['./client-orders.component.css']
})
export class ClientOrdersComponent implements OnInit {

  // Lista de artículos de pedidos desde la API
  orderItems: PurchaseOrderDetails[] = [];

  /**
   * Getter que agrupa los artículos de pedido por ID de pedido.
   * Transforma la lista plana de artículos en objetos agrupados
   * con orderId y array de artículos. Ordenado por ID de pedido descendente.
   */
  get groupedOrders(): { orderId: number; items: PurchaseOrderDetails[] }[] {
    const map = new Map<number, PurchaseOrderDetails[]>();
    for (const item of this.orderItems) {
      const id = item.order.id;
      if (!map.has(id)) map.set(id, []);
      map.get(id)!.push(item);
    }
    return Array.from(map.entries())
      .map(([orderId, items]) => ({ orderId, items }))
      .sort((a, b) => b.orderId - a.orderId);
  }

  /**
   * Calcula el precio total de un pedido completo incluyendo todos los artículos y sus adicionales.
   * @param items - Array de PurchaseOrderDetails que representa los artículos de un pedido
   * @returns Precio total como número
   */
  getOrderTotal(items: PurchaseOrderDetails[]): number {
    return items.reduce((sum, item) => {
      const additionalsTotal = (item.additionals ?? []).reduce((a, x) => a + x.additional.price, 0);
      return sum + (item.plate.price + additionalsTotal) * item.quantity;
    }, 0);
  }

  /**
   * Retorna clase CSS según el estado del pedido para estilizar el badge de estado.
   * @param status - El estado actual del pedido (ej: 'Entregado', 'En preparación', 'Pendiente', 'Cancelado')
   * @returns String con la clase CSS para el badge de estado
   */
  getStatusClass(status: string): string {
    switch (status) {
      case 'Entregado': return 'status-delivered';
      case 'En preparación': return 'status-preparing';
      case 'Pendiente': return 'status-pending';
      case 'Cancelado': return 'status-cancelled';
      default: return 'status-default';
    }
  }

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private orderDetailsService: OrderDetailsService
  ) { }

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.orderDetailsService.selectOrdersByClientId(id).subscribe(
      (orderDetails) => {
        this.orderItems = orderDetails;
      }
    );
  }

  /**
   * Calcula el precio total de un artículo individual incluyendo sus adicionales y cantidad.
   * @param item - El PurchaseOrderDetails para calcular el total
   * @returns Precio total como número
   */
  getItemTotal(item: PurchaseOrderDetails): number {
    const additionalsTotal = (item.additionals ?? []).reduce((a, x) => a + x.additional.price, 0);
    return (item.plate.price + additionalsTotal) * item.quantity;
  }

}
