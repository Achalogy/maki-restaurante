import { Injectable } from '@angular/core';
import { Client } from '../interfaces/client.interface';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  constructor(
      private http: HttpClient
    ) { }

  selectAll(): Observable<Client[]> {
    return this.http.get<Client[]>('http://localhost:8080/api/v1/client')
  }

  selectById(id: number): Observable<Client> {
    return this.http.get<Client>(`http://localhost:8080/api/v1/client/${id}`)
  }

  create(client: Partial<Omit<Client, 'id'>>): Observable<Client> {
    return this.http.post<Client>(`http://localhost:8080/api/v1/client`, client)
  }

  update(id: number, data: Partial<Client>): Observable<Client> {
    return this.http.post<Client>(`http://localhost:8080/api/v1/client/${id}`, data)
  }

  delete(id: number): Observable<Boolean> {
    return this.http.delete<Boolean>(`http://localhost:8080/api/v1/client/${id}`)
  }
}
