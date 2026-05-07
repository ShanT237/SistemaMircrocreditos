import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Solicitud } from '../models/solicitud';

@Injectable({ providedIn: 'root' })
export class SolicitudService {
  private url = 'http://localhost:8080/api/solicitudes';

  constructor(private http: HttpClient) {}

  listar(): Observable<Solicitud[]> {
    return this.http.get<Solicitud[]>(this.url);
  }

  buscarPorId(id: number): Observable<Solicitud> {
    return this.http.get<Solicitud>(`${this.url}/${id}`);
  }

  crear(solicitanteId: number, productoId: number, operadorId: number, solicitud: Solicitud): Observable<Solicitud> {
    const params = new HttpParams()
      .set('solicitanteId', solicitanteId)
      .set('productoId', productoId)
      .set('operadorId', operadorId);
    return this.http.post<Solicitud>(this.url, solicitud, { params });
  }

  aprobar(id: number): Observable<Solicitud> {
    return this.http.put<Solicitud>(`${this.url}/${id}/aprobar`, {});
  }

  rechazar(id: number): Observable<Solicitud> {
    return this.http.put<Solicitud>(`${this.url}/${id}/rechazar`, {});
  }
}