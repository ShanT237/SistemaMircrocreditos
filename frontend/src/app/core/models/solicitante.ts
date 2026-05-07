export interface Solicitante {
  id?: number;
  cedula: string;
  nombre: string;
  email: string;
  telefono: string;
  ingresos: number;
  estado?: 'ACTIVO' | 'INACTIVO' | 'BLOQUEADO';
  fechaCreacion?: string;
}