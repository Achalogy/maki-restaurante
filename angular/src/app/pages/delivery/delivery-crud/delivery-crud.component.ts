import { Component, OnInit } from '@angular/core';
import { Delivery } from 'src/app/interfaces/delivery.interface';
import { DeliveryService } from 'src/app/service/data/delivery.service';

/**
 * DeliveryCrudComponent
 * Página de administración de domiciliarios.
 * Permite: crear, editar, eliminar y activar/desactivar domiciliarios.
 * Accesible desde el portal de administrador (/admin → /delivery/crud).
 */
@Component({
  selector: 'app-delivery-crud',
  templateUrl: './delivery-crud.component.html',
  styleUrls: ['./delivery-crud.component.css']
})
export class DeliveryCrudComponent implements OnInit {

  /** Lista de todos los domiciliarios cargados desde el backend */
  deliveryList: Delivery[] = [];

  /** Controla la visibilidad del modal */
  showModal = false;

  /** true = modo edición, false = modo creación */
  isEditing = false;

  /**
   * Objeto de formulario reactivo usado en el modal.
   * Se reinicia al abrir el modal de creación y se carga con datos
   * al abrir el modal de edición.
   */
  form: Partial<Delivery> = this.emptyForm();

  /** ID del domiciliario en edición (null si se está creando uno nuevo) */
  private editingId: number | null = null;

  constructor(private deliveryService: DeliveryService) {}

  ngOnInit(): void {
    this.loadList();
  }

  /** Carga (o recarga) la lista de domiciliarios desde el backend */
  loadList(): void {
    this.deliveryService.selectAll().subscribe(list => {
      this.deliveryList = list;
    });
  }

  // ─── Modal ────────────────────────────────────────────────────────────────

  /** Abre el modal en modo CREAR con el formulario vacío */
  openCreateModal(): void {
    this.isEditing = false;
    this.editingId = null;
    this.form = this.emptyForm();
    this.showModal = true;
  }

  /** Abre el modal en modo EDITAR cargando los datos del domiciliario */
  openEditModal(d: Delivery): void {
    this.isEditing = true;
    this.editingId = d.id;
    // Copia los datos para no mutar el objeto de la tabla directamente
    this.form = { ...d };
    this.showModal = true;
  }

  /** Cierra el modal y limpia el formulario */
  closeModal(): void {
    this.showModal = false;
    this.form = this.emptyForm();
    this.editingId = null;
  }

  // ─── Operaciones CRUD ─────────────────────────────────────────────────────

  /**
   * Guarda el domiciliario.
   * Si isEditing es true llama a update(), si no llama a create().
   */
  saveDelivery(): void {
    if (this.isEditing && this.editingId !== null) {
      // Actualizar domiciliario existente
      this.deliveryService.update(this.editingId, this.form as Delivery).subscribe(() => {
        this.closeModal();
        this.loadList();
      });
    } else {
      // Crear nuevo domiciliario
      this.deliveryService.create(this.form as Omit<Delivery, 'id'>).subscribe(() => {
        this.closeModal();
        this.loadList();
      });
    }
  }

  /** Elimina un domiciliario tras confirmación del usuario */
  deleteDelivery(id: number): void {
    if (confirm('¿Estás seguro de que deseas eliminar este domiciliario?')) {
      this.deliveryService.delete(id).subscribe(() => {
        this.loadList();
      });
    }
  }

  /**
   * Activa o desactiva un domiciliario (toggle de disponibilidad).
   * Llama al servicio que envía solo el campo `available` modificado.
   */
  toggleAvailable(d: Delivery): void {
    this.deliveryService.toggleAvailability(d).subscribe(() => {
      this.loadList();
    });
  }

  // ─── Utilidades ───────────────────────────────────────────────────────────

  /** Devuelve un objeto vacío con los valores por defecto del formulario */
  private emptyForm(): Partial<Delivery> {
    return {
      name: '',
      phone: '',
      national_id: '',
      available: true
    };
  }
}
