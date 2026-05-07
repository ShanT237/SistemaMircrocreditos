import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Desembolso } from '../models/desembolso';

@Injectable({ providedIn: 'root' })
export class DesembolsoService {
  private url = 'http://localhost:8080/api/desembolsos';

  constructor(private http: HttpClient) {}

  listar(): Observable<Desembolso[]> {
    return this.http.get<Desembolso[]>(this.url);
  }

  buscarPorId(id: number): Observable<Desembolso> {
    return this.http.get<Desembolso>(`${this.url}/${id}`);
  }

  registrar(solicitudId: number, operadorId: number, desembolso: Desembolso): Observable<Desembolso> {
    const params = new HttpParams()
      .set('solicitudId', solicitudId)
      .set('operadorId', operadorId);
    return this.http.post<Desembolso>(this.url, desembolso, { params });
  }
}