import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', redirectTo: 'operadores', pathMatch: 'full' },
  {
    path: 'operadores',
    loadComponent: () => import('./features/operadores/operador-list/operador-list')
      .then(m => m.OperadorListComponent)
  },
  {
    path: 'solicitantes',
    loadComponent: () => import('./features/solicitantes/solicitante-list/solicitante-list')
      .then(m => m.SolicitanteListComponent)
  },
  {
    path: 'productos',
    loadComponent: () => import('./features/productos/producto-list/producto-list')
      .then(m => m.ProductoListComponent)
  },
  {
    path: 'solicitudes',
    loadComponent: () => import('./features/solicitudes/solicitud-list/solicitud-list')
      .then(m => m.SolicitudListComponent)
  },
  {
    path: 'desembolsos',
    loadComponent: () => import('./features/desembolsos/desembolso-list/desembolso-list')
      .then(m => m.DesembolsoListComponent)
  },
  { path: '**', redirectTo: 'operadores' }
];