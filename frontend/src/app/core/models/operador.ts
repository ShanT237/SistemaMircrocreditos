export interface Operador {
  id?: number;
  nombre: string;
  email: string;
  rol: 'VALORADOR' | 'APROBADOR' | 'ANALISTA';
  activo: boolean;
}