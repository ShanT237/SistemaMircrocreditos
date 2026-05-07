import { Solicitante } from './solicitante';
import { ProductoCredito } from './producto';
import { Operador } from './operador';

export interface Solicitud {
  id?: number;
  solicitante?: Solicitante;
  producto?: ProductoCredito;
  operador?: Operador;
  monto: number;
  plazoMeses: number;
  estado?: 'PENDIENTE' | 'APROBADA' | 'RECHAZADA' | 'CANCELADA';
  fechaSolicitud?: string;
  fechaActualizacion?: string;
}