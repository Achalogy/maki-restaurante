import { Injectable } from '@angular/core';
import { Category } from '../interfaces/category.interface';
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

  selectAll(): Observable<Category[]> {
    return this.http.get<Category[]>(this.API_URL);
  }

  selectById(id: number): Observable<Category> {
    return this.http.get<Category>(`${this.API_URL}/${id}`);
  }

  create(category: Partial<Omit<Category, 'id'>>): Observable<Category> {
    return this.http.post<Category>(this.API_URL, category);
  }

  update(id: number, data: Partial<Category>): Observable<Category> {
    return this.http.post<Category>(`${this.API_URL}/${id}`, data);
  }

  delete(id: number): Observable<boolean> {
    return this.http.delete<boolean>(`${this.API_URL}/${id}`);
  }
}