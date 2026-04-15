import { Injectable } from '@angular/core';
import { Aditional } from '../interfaces/aditional.interface';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { AditionalCategory } from '../interfaces/aditional-category.interface';
import { Category } from '../interfaces/category.interface';

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

  selectAllCategories(): Observable<AditionalCategory[]> {
    return this.http.get<AditionalCategory[]>(`${this.API_URL}/categories`);
  }

  selectAllCategoriesByAditionalId(id: number): Observable<AditionalCategory[]> {
    return this.http.get<AditionalCategory[]>(`${this.API_URL}/categories?aditionalId=${id}`);
  }

  setCategories(id: number, categories: Category[]): Observable<AditionalCategory[]> {
    return this.http.post<AditionalCategory[]>(`${this.API_URL}/${id}/categories`, categories);
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