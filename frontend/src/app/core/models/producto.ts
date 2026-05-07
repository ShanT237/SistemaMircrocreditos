export interface ProductoCredito {
  id?: number;
  nombre: string;
  montoMin: number;
  montoMax: number;
  tasaInteres: number;
  plazoMaxMeses: number;
  activo: boolean;
}