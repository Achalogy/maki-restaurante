import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from 'rxjs';
import { Operator } from 'src/app/interfaces/operator.interface';

@Injectable({
  providedIn: 'root'
})
export class OperatorService {

  constructor(private http: HttpClient) { }

  selectAll(): Observable<Operator[]> {
    return this.http.get<Operator[]>("http://localhost:8080/api/v1/operator");
  }

  selectById(id: number): Observable<Operator> {
    return this.http.get<Operator>(`http://localhost:8080/api/v1/operator/${id}`);
  }

  create(operator: Partial<Omit<Operator, 'id'>>): Observable<Operator> {
    return this.http.post<Operator>("http://localhost:8080/api/v1/operator", operator);
  }

  update(id: number, data: Partial<Operator>): Observable<Operator> {
    return this.http.post<Operator>(`http://localhost:8080/api/v1/operator/${id}`, data);
  }

  delete(id: number): Observable<boolean> {
    return this.http.delete<boolean>(`http://localhost:8080/api/v1/operator/${id}`);
  }

    logIn(credentials: { username: string; password: string }): Observable<Operator> {
    return this.http.post<Operator>(`http://localhost:8080/api/v1/operator/log-in`, credentials);
  }

}