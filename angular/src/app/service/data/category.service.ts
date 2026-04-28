import { Injectable } from '@angular/core';
import { Category } from 'src/app/interfaces/category.interface';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private readonly API_URL = 'http://localhost:8080/api/v1/category';

  constructor(
    private http: HttpClient
  ) { }

  /**
   * Obtiene todas las categorías registradas en el servidor.
   * Devuelve un Observable que emite un arreglo de Category.
   */
  selectAll(): Observable<Category[]> {
    return this.http.get<Category[]>(this.API_URL);
  }

  /**
   * Obtiene una categoría específica según su identificador.
   * @param id Identificador de la categoría a recuperar.
   * Devuelve un Observable que emite la categoría solicitada.
   */
  selectById(id: number): Observable<Category> {
    return this.http.get<Category>(`${this.API_URL}/${id}`);
  }

  /**
   * Crea una nueva categoría enviando los datos al servidor.
   * @param category Datos de la categoría sin el campo id.
   * Devuelve un Observable que emite la categoría creada.
   */
  create(category: Partial<Omit<Category, 'id'>>): Observable<Category> {
    return this.http.post<Category>(this.API_URL, category);
  }

  /**
   * Actualiza una categoría existente por su identificador.
   * @param id Identificador de la categoría a actualizar.
   * @param data Datos modificados de la categoría.
   * Devuelve un Observable que emite la categoría actualizada.
   */
  update(id: number, data: Partial<Category>): Observable<Category> {
    return this.http.post<Category>(`${this.API_URL}/${id}`, data);
  }

  /**
   * Elimina una categoría a partir de su identificador.
   * @param id Identificador de la categoría a eliminar.
   * Devuelve un Observable que emite true si la eliminación fue exitosa.
   */
  delete(id: number): Observable<boolean> {
    return this.http.delete<boolean>(`${this.API_URL}/${id}`);
  }
}