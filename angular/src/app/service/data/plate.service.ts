import { Injectable } from '@angular/core';
import { Plate } from 'src/app/interfaces/plate.interface';
import { HttpClient } from "@angular/common/http"
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PlateService {

  /**
   * Servicio para realizar peticiones HTTP relacionadas con platos.
   */
  constructor(
    private http: HttpClient
  ) { }

  /**
   * Obtiene todos los platos disponibles desde la API.
   * @returns Observable<Plate[]> con la lista de platos.
   */
  selectAll(): Observable<Plate[]> {
    return this.http.get<Plate[]>('http://localhost:8080/api/v1/plate')
  }
  
  /**
   * Obtiene un plato específico por su id.
   * @param id Identificador del plato a recuperar.
   * @returns Observable<Plate> con el plato encontrado.
   */
  selectById(id: number): Observable<Plate> {
    return this.http.get<Plate>(`http://localhost:8080/api/v1/plate/${id}`)
  }

  /**
   * Crea un nuevo plato en la API.
   * Extrae el id de la categoría asociada y envía el payload con los datos del plato.
   * @param plate Datos parciales del plato sin el id.
   * @returns Observable<Plate> con el plato creado.
   */
  create(plate: Partial<Omit<Plate, 'id'>>): Observable<Plate> {
    const categoryId = plate.category?.id;

    const payload = {
      name: plate.name,
      price: plate.price,
      description: plate.description,
      urlImage: plate.urlImage,
      available: plate.available
    }

    return this.http.post<Plate>(`http://localhost:8080/api/v1/plate?categoryId=${categoryId}`, payload)
  }

  /**
   * Actualiza los datos de un plato existente por su id.
   * @param id Identificador del plato a actualizar.
   * @param data Datos parciales del plato a modificar.
   * @returns Observable<Plate> con el plato actualizado.
   */
  update(id: number, data: Partial<Plate>): Observable<Plate> {
    return this.http.post<Plate>(`http://localhost:8080/api/v1/plate/${id}`, data)
  }

  /**
   * Actualiza únicamente la categoría asociada a un plato existente.
   * @param id Identificador del plato.
   * @param categoryId Identificador de la nueva categoría.
   * @returns Observable<Plate> con el plato actualizado.
   */
  updateCategory(id: number, categoryId: number): Observable<Plate> {
    return this.http.post<Plate>(`http://localhost:8080/api/v1/plate/${id}/category/${categoryId}`, {})
  }

  /**
   * Elimina un plato por su id.
   * @param id Identificador del plato a eliminar.
   * @returns Observable<boolean> indicando si la eliminación fue exitosa.
   */
  delete(id: number): Observable<boolean> {
    return this.http.delete<boolean>(`http://localhost:8080/api/v1/plate/${id}`)
  }

}
