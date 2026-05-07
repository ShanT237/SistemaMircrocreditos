import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, MatListModule, MatIconModule],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.scss'
})
export class SidebarComponent {
  menu = [
    { label: 'Operadores', icon: 'manage_accounts', route: '/operadores' },
    { label: 'Solicitantes', icon: 'people', route: '/solicitantes' },
    { label: 'Productos', icon: 'inventory', route: '/productos' },
    { label: 'Solicitudes', icon: 'description', route: '/solicitudes' },
    { label: 'Desembolsos', icon: 'payments', route: '/desembolsos' },
  ];
}