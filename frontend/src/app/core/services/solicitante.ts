import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Solicitante } from '../models/solicitante';

@Injectable({ providedIn: 'root' })
export class SolicitanteService {
  private url = 'http://localhost:8080/api/solicitantes';

  constructor(private http: HttpClient) {}

  listar(): Observable<Solicitante[]> {
    return this.http.get<Solicitante[]>(this.url);
  }

  buscarPorId(id: number): Observable<Solicitante> {
    return this.http.get<Solicitante>(`${this.url}/${id}`);
  }

  buscarPorCedula(cedula: string): Observable<Solicitante> {
    return this.http.get<Solicitante>(`${this.url}/cedula/${cedula}`);
  }

  crear(solicitante: Solicitante): Observable<Solicitante> {
    return this.http.post<Solicitante>(this.url, solicitante);
  }

  actualizar(id: number, solicitante: Solicitante): Observable<Solicitante> {
    return this.http.put<Solicitante>(`${this.url}/${id}`, solicitante);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}