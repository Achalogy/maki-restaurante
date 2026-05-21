import { Injectable } from "@angular/core";
import { Client } from "src/app/interfaces/client.interface";
import { Observable } from "rxjs";
import { HttpClient } from "@angular/common/http";

@Injectable({
  providedIn: "root",
})
export class ClientService {
  constructor(private http: HttpClient) {}

  /**
   * Obtiene todos los clientes desde el backend.
   * Devuelve un Observable con un arreglo de objetos Client.
   */
  selectAll(): Observable<Client[]> {
    return this.http.get<Client[]>("http://localhost:8080/api/v1/client");
  }

  /**
   * Obtiene un cliente específico por su id.
   * @param id Identificador numérico del cliente.
   * Devuelve un Observable con el objeto Client correspondiente.
   */
  selectById(id: number): Observable<Client> {
    return this.http.get<Client>(`http://localhost:8080/api/v1/client/${id}`);
  }

  /**
   * Crea un nuevo cliente enviando los datos al servidor.
   * @param client Objeto parcial de Client sin el campo id.
   * Devuelve un Observable con el cliente creado.
   */
  create(client: Partial<Omit<Client, "id">>): Observable<Client> {
    return this.http.post<Client>(
      `http://localhost:8080/api/v1/client`,
      client,
    );
  }

  /**
   * Realiza el inicio de sesión de un cliente.
   * @param client Objeto parcial de Client sin el campo id.
   * Devuelve un Observable con el cliente autenticado.
   */
  logIn(client: Partial<Omit<Client, "id">>): Observable<Client> {
    return this.http.post<Client>(
      `http://localhost:8080/api/v1/client/log-in`,
      client,
    );
  }

  /**
   * Actualiza los datos de un cliente existente.
   * @param id Identificador numérico del cliente.
   * @param data Datos parciales del cliente a actualizar.
   * Devuelve un Observable con el cliente actualizado.
   */
  update(id: number, data: Partial<Client>): Observable<Client> {
    return this.http.post<Client>(
      `http://localhost:8080/api/v1/client/${id}`,
      data,
    );
  }

  /**
   * Elimina un cliente por su id.
   * @param id Identificador numérico del cliente a eliminar.
   * Devuelve un Observable con un valor booleano que indica éxito.
   */
  delete(id: number): Observable<Boolean> {
    return this.http.delete<Boolean>(
      `http://localhost:8080/api/v1/client/${id}`,
    );
  }

  /**
   * Trae a mi cliente.
   * Devuelve un Observable con el cliente en base al auth.
   */
  getMe(): Observable<Client> {
    return this.http.get<Client>(`http://localhost:8080/api/v1/client/me`);
  }
}
