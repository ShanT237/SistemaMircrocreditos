import { Solicitud } from './solicitud';
import { Operador } from './operador';

export interface Desembolso {
  id?: number;
  solicitud?: Solicitud;
  operador?: Operador;
  monto: number;
  fechaDesembolso?: string;
  metodoPago: 'TRANSFERENCIA' | 'EFECTIVO' | 'CHEQUE';
  estado?: 'PROGRAMADO' | 'EJECUTADO' | 'FALLIDO';
  cuotaMensual?: number;
}