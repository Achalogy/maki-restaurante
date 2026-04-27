import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Client } from 'src/app/interfaces/client.interface';
import { PurchaseOrderDetails } from 'src/app/interfaces/order-details.interface';
import { ClientService } from 'src/app/service/data/client.service';
import { OrderDetailsService } from 'src/app/service/data/order-details.service';

/**
 * Componente de Perfil de Cliente
 * Maneja la visualización y gestión de datos del perfil del cliente incluyendo:
 * - Información personal (nombre, apellido, correo, teléfono, dirección)
 * - Historial de pedidos con detalles agrupados
 */
@Component({
  selector: 'app-client-profile',
  templateUrl: './client-profile.component.html',
  styleUrls: ['./client-profile.component.css']
})
export class ClientProfileComponent implements OnInit {
  // Datos del cliente actual cargados desde la API
  client: Client | undefined;
  
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
    private clientService: ClientService,
    private route: ActivatedRoute,
    private router: Router,
    private orderDetailsService: OrderDetailsService
  ) {}

  /**
   * Hook del ciclo de vida que inicializa el componente.
   * Carga los datos del cliente desde el ID de la ruta y obtiene su historial de pedidos.
   */
  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.clientService.selectById(id).subscribe(client => {
      if (!client) this.router.navigate(['/client/crud']);
      else this.client = client;
    });
    this.orderDetailsService.selectOrdersByClientId(id).subscribe(items => {
      this.orderItems = items;
    });
  }

  /**
   * Maneja el envío del formulario para actualizar la información del perfil del cliente.
   * Llama al servicio de cliente para actualizar y navega al perfil en caso de éxito.
   */
  onUpdate(): void {
    if (this.client) {
      this.clientService.update(this.client.id, this.client).subscribe((client) => {
        this.router.navigate([`/client/${client.id}`]);
      });
    }
  }

  /**
   * Maneja la eliminación de la cuenta con diálogo de confirmación.
   * Muestra alerta de confirmación, elimina la cuenta si se confirma y redirige a la página CRUD.
   */
  onDelete(): void {
    const confirmed = confirm('¿Estás seguro de eliminar tu cuenta? Esta acción no se puede deshacer.');
    if (this.client && confirmed) {
      this.clientService.delete(this.client.id).subscribe(() => {
        this.router.navigate(['/client/crud']);
      });
    }
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