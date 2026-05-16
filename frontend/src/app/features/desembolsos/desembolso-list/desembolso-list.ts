import { Component, OnInit, ChangeDetectorRef, inject } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog } from '@angular/material/dialog';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { CommonModule } from '@angular/common';
import { DesembolsoService } from '../../../core/services/desembolso';
import { Desembolso } from '../../../core/models/desembolso';
import { DesembolsoFormComponent } from '../desembolso-form/desembolso-form';

@Component({
  selector: 'app-desembolso-list',
  standalone: true,
  imports: [MatTableModule, MatButtonModule, MatIconModule, MatProgressSpinnerModule, CommonModule],
  templateUrl: './desembolso-list.html',
  styleUrl: './desembolso-list.scss'
})
export class DesembolsoListComponent implements OnInit {
  desembolsos: Desembolso[] = [];
  columnas = ['solicitante', 'monto', 'cuotaMensual', 'metodoPago', 'estado', 'fecha', 'acciones'];
  cargando = true;
  error = '';

  private desembolsoService = inject(DesembolsoService);
  private dialog = inject(MatDialog);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit() { this.cargar(); }

  cargar() {
    this.cargando = true;
    this.error = '';
    this.desembolsoService.listar().subscribe({
      next: data => { this.desembolsos = data; this.cargando = false; this.cdr.detectChanges(); },
      error: () => { this.error = 'No se pudo conectar al servidor.'; this.cargando = false; this.cdr.detectChanges(); }
    });
  }

  abrirFormulario() {
    const ref = this.dialog.open(DesembolsoFormComponent, { width: '520px', data: null });
    ref.afterClosed().subscribe(result => { if (result) this.cargar(); });
  }

  getEstadoClass(estado: string): string {
    const map: Record<string, string> = {
      PROGRAMADO: 'badge-programado',
      EJECUTADO: 'badge-ejecutado',
      FALLIDO: 'badge-fallido'
    };
    return map[estado] ?? '';
  }
}