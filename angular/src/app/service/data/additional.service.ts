import { Injectable } from '@angular/core';
import { Additional } from 'src/app/interfaces/additional.interface';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { AdditionalCategory } from 'src/app/interfaces/additional-category.interface';
import { Category } from 'src/app/interfaces/category.interface';

@Injectable({
  providedIn: 'root'
})
export class AdditionalService {

  /**
   * URL base del endpoint de adicionales.
   */
  private readonly API_URL = 'http://localhost:8080/api/v1/additional';

  constructor(
    private http: HttpClient
  ) { }

  /**
   * Obtiene todos los adicionales.
   * @returns Observable con un arreglo de objetos Additional.
   */
  selectAll(): Observable<Additional[]> {
    return this.http.get<Additional[]>(this.API_URL);
  }

  /**
   * Obtiene todas las categorías de adicionales.
   * @returns Observable con un arreglo de objetos AdditionalCategory.
   */
  selectAllCategories(): Observable<AdditionalCategory[]> {
    return this.http.get<AdditionalCategory[]>(`${this.API_URL}/categories`);
  }

  /**
   * Obtiene todas las categorías asociadas a un adicional específico por su ID.
   * @param id El ID del adicional.
   * @returns Observable con un arreglo de objetos AdditionalCategory.
   */
  selectAllCategoriesByAdditionalId(id: number): Observable<AdditionalCategory[]> {
    return this.http.get<AdditionalCategory[]>(`${this.API_URL}/categories?additionalId=${id}`);
  }

  /**
   * Asigna categorías a un adicional específico por su ID.
   * @param id El ID del adicional.
   * @param categories Arreglo de categorías a asignar.
   * @returns Observable con un arreglo de objetos AdditionalCategory.
   */
  setCategories(id: number, categories: Category[]): Observable<AdditionalCategory[]> {
    return this.http.post<AdditionalCategory[]>(`${this.API_URL}/${id}/categories`, categories);
  }

  /**
   * Obtiene un adicional específico por su ID.
   * @param id El ID del adicional.
   * @returns Observable con un objeto Additional.
   */
  selectById(id: number): Observable<Additional> {
    return this.http.get<Additional>(`${this.API_URL}/${id}`);
  }

  /**
   * Crea un nuevo adicional.
   * @param additional Los datos del adicional a crear (sin el ID).
   * @returns Observable con el objeto Additional creado.
   */
  create(additional: Partial<Omit<Additional, 'id'>>): Observable<Additional> {
    return this.http.post<Additional>(this.API_URL, additional);
  }

  /**
   * Actualiza un adicional específico por su ID.
   * @param id El ID del adicional.
   * @param data Los datos a actualizar.
   * @returns Observable con el objeto Additional actualizado.
   */
  update(id: number, data: Partial<Additional>): Observable<Additional> {
    return this.http.post<Additional>(`${this.API_URL}/${id}`, data);
  }

  /**
   * Elimina un adicional específico por su ID.
   * @param id El ID del adicional.
   * @returns Observable con un booleano indicando si la eliminación fue exitosa.
   */
  delete(id: number): Observable<boolean> {
    return this.http.delete<boolean>(`${this.API_URL}/${id}`);
  }

  /**
   * Obtiene todos los adicionales asociados a una categoría específica por su ID.
   * @param id El ID de la categoría.
   * @returns Observable con un arreglo de objetos Additional.
   */
  selectByCategoryId(id: number): Observable<Additional[]> {
    return this.http.get<Additional[]>(`${this.API_URL}?categoryId=${id}`);
  }
}