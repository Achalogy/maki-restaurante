import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from 'rxjs';
import { Administrator } from 'src/app/interfaces/administrator.interface';

@Injectable({
  providedIn: 'root'
})
export class AdministratorService {

  constructor(private http: HttpClient) { }

  selectAll(): Observable<Administrator[]> {
    return this.http.get<Administrator[]>("http://localhost:8080/api/v1/admin");
  }

  selectById(id: number): Observable<Administrator> {
    return this.http.get<Administrator>(`http://localhost:8080/api/v1/admin/${id}`);
  }

  create(administrator: Partial<Omit<Administrator, 'id'>>): Observable<Administrator> {
    return this.http.post<Administrator>("http://localhost:8080/api/v1/admin", administrator);
  }

  update(id: number, data: Partial<Administrator>): Observable<Administrator> {
    return this.http.post<Administrator>(`http://localhost:8080/api/v1/admin/${id}`, data);
  }

  delete(id: number): Observable<boolean> {
    return this.http.delete<boolean>(`http://localhost:8080/api/v1/admin/${id}`);
  }

    logIn(credentials: { username: string; password: string }): Observable<Administrator> {
    return this.http.post<Administrator>(`http://localhost:8080/api/v1/admin/log-in`, credentials);
  }

}