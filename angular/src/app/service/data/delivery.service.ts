import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Delivery } from 'src/app/interfaces/delivery.interface';

/**
 * DeliveryService
 * Maneja todas las peticiones HTTP relacionadas con domiciliarios.
 * Base URL: /api/v1/delivery (Spring Boot backend)
 */
@Injectable({
  providedIn: 'root'
})
export class DeliveryService {

  private readonly API_URL = 'http://localhost:8080/api/v1/delivery';

  constructor(private http: HttpClient) {}

  /** Obtiene todos los domiciliarios */
  selectAll(): Observable<Delivery[]> {
    return this.http.get<Delivery[]>(this.API_URL);
  }

  /** Obtiene un domiciliario por su ID */
  selectById(id: number): Observable<Delivery> {
    return this.http.get<Delivery>(`${this.API_URL}/${id}`);
  }

  /** Crea un nuevo domiciliario */
  create(delivery: Omit<Delivery, 'id'>): Observable<Delivery> {
    return this.http.post<Delivery>(this.API_URL, delivery);
  }

  /** Actualiza un domiciliario existente */
  update(id: number, data: Partial<Delivery>): Observable<Delivery> {
    return this.http.post<Delivery>(`${this.API_URL}/${id}`, data);
  }

  /** Elimina un domiciliario por su ID */
  delete(id: number): Observable<boolean> {
    return this.http.delete<boolean>(`${this.API_URL}/${id}`);
  }

  /**
   * Activa o desactiva un domiciliario (toggle de disponibilidad).
   * Envía solo el campo `available` para no sobreescribir otros datos.
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
