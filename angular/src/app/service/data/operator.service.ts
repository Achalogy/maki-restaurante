import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from 'rxjs';
import { Operator } from 'src/app/interfaces/operator.interface';

@Injectable({
  providedIn: 'root'
})
export class OperatorService {

  constructor(private http: HttpClient) { }

  /**
   * Recupera todos los operadores desde el backend.
   * Retorna un Observable con un arreglo de Operator.
   */
  selectAll(): Observable<Operator[]> {
    return this.http.get<Operator[]>("http://localhost:8080/api/v1/operator");
  }

  /**
   * Recupera un operador por su identificador.
   * @param id Identificador numérico del operador.
   * Retorna un Observable con el Operator solicitado.
   */
  selectById(id: number): Observable<Operator> {
    return this.http.get<Operator>(`http://localhost:8080/api/v1/operator/${id}`);
  }

  /**
   * Crea un nuevo operador en el backend.
   * @param operator Objeto parcial sin el campo id.
   * Retorna un Observable con el Operator creado.
   */
  create(operator: Partial<Omit<Operator, 'id'>>): Observable<Operator> {
    return this.http.post<Operator>("http://localhost:8080/api/v1/operator", operator);
  }

  /**
   * Actualiza los datos de un operador existente.
   * @param id Identificador del operador a actualizar.
   * @param data Datos parciales del operador.
   * Retorna un Observable con el Operator actualizado.
   */
  update(id: number, data: Partial<Operator>): Observable<Operator> {
    return this.http.post<Operator>(`http://localhost:8080/api/v1/operator/${id}`, data);
  }

  /**
   * Elimina un operador por su identificador.
   * @param id Identificador del operador a eliminar.
   * Retorna un Observable booleano que indica si la eliminación fue exitosa.
   */
  delete(id: number): Observable<boolean> {
    return this.http.delete<boolean>(`http://localhost:8080/api/v1/operator/${id}`);
  }

  /**
   * Realiza el inicio de sesión de un operador.
   * @param credentials Credenciales con usuario y contraseña.
   * Retorna un Observable con el Operator autenticado.
   */
  logIn(credentials: { username: string; password: string }): Observable<Operator> {
    return this.http.post<Operator>(`http://localhost:8080/api/v1/operator/log-in`, credentials);
  }

}