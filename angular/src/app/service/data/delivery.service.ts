import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Delivery } from 'src/app/interfaces/delivery.interface';

/**
 * Servicio para gestionar las operaciones HTTP relacionadas con domiciliarios.
 * Utiliza el endpoint base /api/v1/delivery del backend.
 */
@Injectable({
  providedIn: 'root'
})
export class DeliveryService {

  private readonly API_URL = 'http://localhost:8080/api/v1/delivery';

  constructor(private http: HttpClient) {}

  /**
   * Obtiene todos los domiciliarios.
   * Realiza una petición GET al endpoint base y devuelve un arreglo de Delivery.
   */
  selectAll(): Observable<Delivery[]> {
    return this.http.get<Delivery[]>(this.API_URL);
  }

  /**
   * Obtiene un domiciliario por su ID.
   * Realiza una petición GET a /api/v1/delivery/{id}.
   * @param id Identificador del domiciliario.
   */
  selectById(id: number): Observable<Delivery> {
    return this.http.get<Delivery>(`${this.API_URL}/${id}`);
  }

  /**
   * Crea un nuevo domiciliario.
   * Envía los datos del domiciliario al servidor para persistirlos.
   * @param delivery Datos del domiciliario sin el campo id.
   */
  create(delivery: Omit<Delivery, 'id'>): Observable<Delivery> {
    return this.http.post<Delivery>(this.API_URL, delivery);
  }

  /**
   * Actualiza un domiciliario existente.
   * Envía los campos a modificar al endpoint del domiciliario.
   * @param id Identificador del domiciliario a actualizar.
   * @param data Campos parciales del domiciliario que se desean cambiar.
   */
  update(id: number, data: Partial<Delivery>): Observable<Delivery> {
    return this.http.post<Delivery>(`${this.API_URL}/${id}`, data);
  }

  /**
   * Elimina un domiciliario por su ID.
   * Realiza una petición DELETE al endpoint del recurso.
   * @param id Identificador del domiciliario a eliminar.
   */
  delete(id: number): Observable<boolean> {
    return this.http.delete<boolean>(`${this.API_URL}/${id}`);
  }

  /**
   * Cambia la disponibilidad de un domiciliario.
   * Envía únicamente el campo available negado para alternar su estado.
   * @param delivery Domiciliario cuyo estado de disponibilidad se desea cambiar.
   */
  toggleAvailability(delivery: Delivery): Observable<Delivery> {
    return this.http.post<Delivery>(`${this.API_URL}/${delivery.id}`, {
      available: !delivery.available
    });
  }
  
  selectActive(): Observable<Delivery[]> {
    return this.http.get<Delivery[]>(`${this.API_URL}/?active=1`)
  }
}
