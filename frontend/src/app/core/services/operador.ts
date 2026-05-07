import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Operador } from '../models/operador';

@Injectable({ providedIn: 'root' })
export class OperadorService {
  private url = 'http://localhost:8080/api/operadores';

  constructor(private http: HttpClient) {}

  listar(): Observable<Operador[]> {
    return this.http.get<Operador[]>(this.url);
  }

  buscarPorId(id: number): Observable<Operador> {
    return this.http.get<Operador>(`${this.url}/${id}`);
  }

  crear(operador: Operador): Observable<Operador> {
    return this.http.post<Operador>(this.url, operador);
  }

  actualizar(id: number, operador: Operador): Observable<Operador> {
    return this.http.put<Operador>(`${this.url}/${id}`, operador);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}