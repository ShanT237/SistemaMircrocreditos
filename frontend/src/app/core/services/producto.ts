import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductoCredito } from '../models/producto';

@Injectable({ providedIn: 'root' })
export class ProductoService {
  private url = 'http://localhost:8080/api/productos';

  constructor(private http: HttpClient) {}

  listar(soloActivos = true): Observable<ProductoCredito[]> {
    return this.http.get<ProductoCredito[]>(`${this.url}?soloActivos=${soloActivos}`);
  }

  buscarPorId(id: number): Observable<ProductoCredito> {
    return this.http.get<ProductoCredito>(`${this.url}/${id}`);
  }

  crear(producto: ProductoCredito): Observable<ProductoCredito> {
    return this.http.post<ProductoCredito>(this.url, producto);
  }

  actualizar(id: number, producto: ProductoCredito): Observable<ProductoCredito> {
    return this.http.put<ProductoCredito>(`${this.url}/${id}`, producto);
  }
}