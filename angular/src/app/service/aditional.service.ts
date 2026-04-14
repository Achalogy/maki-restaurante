import { Injectable } from '@angular/core';
import { Aditional } from '../interfaces/aditional.interface';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AditionalService {

  private readonly API_URL = 'http://localhost:8080/api/v1/aditional';

  constructor(
    private http: HttpClient
  ) { }

  selectAll(): Observable<Aditional[]> {
    return this.http.get<Aditional[]>(this.API_URL);
  }

  selectById(id: number): Observable<Aditional> {
    return this.http.get<Aditional>(`${this.API_URL}/${id}`);
  }

  create(aditional: Partial<Omit<Aditional, 'id'>>): Observable<Aditional> {
    return this.http.post<Aditional>(this.API_URL, aditional);
  }

  update(id: number, data: Partial<Aditional>): Observable<Aditional> {
    return this.http.post<Aditional>(`${this.API_URL}/${id}`, data);
  }

  delete(id: number): Observable<boolean> {
    return this.http.delete<boolean>(`${this.API_URL}/${id}`);
  }

  selectByCategoryId(id: number): Observable<Aditional[]> {
    return this.http.get<Aditional[]>(`${this.API_URL}?categoryId=${id}`);
  }
}