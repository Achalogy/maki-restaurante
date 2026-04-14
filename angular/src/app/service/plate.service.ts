import { Injectable } from '@angular/core';
import { Plate } from '../interfaces/plate.interface';
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
    return this.http.post<Plate>(`http://localhost:8080/api/v1/plate`, plate)
  }

  update(id: number, data: Partial<Plate>): Observable<Plate> {
    return this.http.post<Plate>(`http://localhost:8080/api/v1/plate/${id}`, data)
  }

  delete(id: number): Observable<Boolean> {
    return this.http.delete<Boolean>(`http://localhost:8080/api/v1/plate/${id}`)
  }

}
