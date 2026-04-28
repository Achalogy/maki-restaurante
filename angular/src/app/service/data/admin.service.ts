import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from 'rxjs';
import { Administrator } from 'src/app/interfaces/administrator.interface';

@Injectable({
  providedIn: 'root'
})
export class AdministratorService {

  constructor(private http: HttpClient) { }

  // Recupera todos los administradores registrados en el backend.
  // Devuelve un Observable con un arreglo de objetos Administrator.
  selectAll(): Observable<Administrator[]> {
    return this.http.get<Administrator[]>("http://localhost:8080/api/v1/admin");
  }

  // Recupera un administrador específico por su identificador.
  // Retorna un Observable con el objeto Administrator encontrado.
  selectById(id: number): Observable<Administrator> {
    return this.http.get<Administrator>(`http://localhost:8080/api/v1/admin/${id}`);
  }

  // Crea un nuevo administrador en el backend.
  // Recibe los datos del administrador sin id y devuelve el administrador creado.
  create(administrator: Partial<Omit<Administrator, 'id'>>): Observable<Administrator> {
    return this.http.post<Administrator>("http://localhost:8080/api/v1/admin", administrator);
  }

  // Actualiza un administrador existente.
  // Envía los campos modificados y devuelve el administrador actualizado.
  update(id: number, data: Partial<Administrator>): Observable<Administrator> {
    return this.http.post<Administrator>(`http://localhost:8080/api/v1/admin/${id}`, data);
  }

  // Elimina un administrador por su identificador.
  // Retorna un Observable con un valor booleano que indica si la eliminación fue exitosa.
  delete(id: number): Observable<boolean> {
    return this.http.delete<boolean>(`http://localhost:8080/api/v1/admin/${id}`);
  }

  // Realiza el inicio de sesión con las credenciales proporcionadas.
  // Retorna un Observable con el administrador autenticado si las credenciales son válidas.
  logIn(credentials: { username: string; password: string }): Observable<Administrator> {
    return this.http.post<Administrator>(`http://localhost:8080/api/v1/admin/log-in`, credentials);
  }

}