import { Injectable } from '@angular/core';
import { Additional } from '../interfaces/additional.interface';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { AdditionalCategory } from '../interfaces/additional-category.interface';
import { Category } from '../interfaces/category.interface';

@Injectable({
  providedIn: 'root'
})
export class AdditionalService {

  private readonly API_URL = 'http://localhost:8080/api/v1/additional';

  constructor(
    private http: HttpClient
  ) { }

  selectAll(): Observable<Additional[]> {
    return this.http.get<Additional[]>(this.API_URL);
  }

  selectAllCategories(): Observable<AdditionalCategory[]> {
    return this.http.get<AdditionalCategory[]>(`${this.API_URL}/categories`);
  }

  selectAllCategoriesByAdditionalId(id: number): Observable<AdditionalCategory[]> {
    return this.http.get<AdditionalCategory[]>(`${this.API_URL}/categories?additionalId=${id}`);
  }

  setCategories(id: number, categories: Category[]): Observable<AdditionalCategory[]> {
    return this.http.post<AdditionalCategory[]>(`${this.API_URL}/${id}/categories`, categories);
  }

  selectById(id: number): Observable<Additional> {
    return this.http.get<Additional>(`${this.API_URL}/${id}`);
  }

  create(additional: Partial<Omit<Additional, 'id'>>): Observable<Additional> {
    return this.http.post<Additional>(this.API_URL, additional);
  }

  update(id: number, data: Partial<Additional>): Observable<Additional> {
    return this.http.post<Additional>(`${this.API_URL}/${id}`, data);
  }

  delete(id: number): Observable<boolean> {
    return this.http.delete<boolean>(`${this.API_URL}/${id}`);
  }

  selectByCategoryId(id: number): Observable<Additional[]> {
    return this.http.get<Additional[]>(`${this.API_URL}?categoryId=${id}`);
  }
}