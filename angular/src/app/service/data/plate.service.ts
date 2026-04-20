import { Injectable } from '@angular/core';
import { Plate } from 'src/app/interfaces/plate.interface';
import { HttpClient } from "@angular/common/http"
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PlateService {

  constructor(
    private http: HttpClient
  ) { }

  selectAll() {
    return this.http.get<Plate[]>('http://localhost:8080/api/v1/plate')
  }
  
  selectById(id: number): Observable<Plate> {
    return this.http.get<Plate>(`http://localhost:8080/api/v1/plate/${id}`)
  }

  create(plate: Partial<Omit<Plate, 'id'>>): Observable<Plate> {
    const categoryId = plate.category?.id;

    const payload = {
      name: plate.name,
      price: plate.price,
      description: plate.description,
      urlImage: plate.urlImage,
      available: plate.available
    }

    return this.http.post<Plate>(`http://localhost:8080/api/v1/plate?categoryId=${categoryId}`, payload)
  }

  update(id: number, data: Partial<Plate>): Observable<Plate> {
    return this.http.post<Plate>(`http://localhost:8080/api/v1/plate/${id}`, data)
  }

  updateCategory(id: number, categoryId: number): Observable<Plate> {
    return this.http.post<Plate>(`http://localhost:8080/api/v1/plate/${id}/category/${categoryId}`, {})
  }

  delete(id: number): Observable<Boolean> {
    return this.http.delete<Boolean>(`http://localhost:8080/api/v1/plate/${id}`)
  }

}
