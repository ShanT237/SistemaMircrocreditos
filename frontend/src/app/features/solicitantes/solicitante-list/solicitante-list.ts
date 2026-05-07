import { Component, OnInit, ChangeDetectorRef, inject } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog } from '@angular/material/dialog';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { CommonModule } from '@angular/common';
import { SolicitanteService } from '../../../core/services/solicitante';
import { Solicitante } from '../../../core/models/solicitante';
import { SolicitanteFormComponent } from '../solicitante-form/solicitante-form';

@Component({
  selector: 'app-solicitante-list',
  standalone: true,
  imports: [MatTableModule, MatButtonModule, MatIconModule, MatProgressSpinnerModule, CommonModule],
  templateUrl: './solicitante-list.html',
  styleUrl: './solicitante-list.scss'
})
export class SolicitanteListComponent implements OnInit {
  solicitantes: Solicitante[] = [];
  columnas = ['nombre', 'cedula', 'email', 'telefono', 'ingresos', 'estado', 'acciones'];
  cargando = true;
  error = '';

  private solicitanteService = inject(SolicitanteService);
  private dialog = inject(MatDialog);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit() { this.cargar(); }

  cargar() {
    this.cargando = true;
    this.error = '';
    this.solicitanteService.listar().subscribe({
      next: data => { this.solicitantes = data; this.cargando = false; this.cdr.detectChanges(); },
      error: () => { this.error = 'No se pudo conectar al servidor.'; this.cargando = false; this.cdr.detectChanges(); }
    });
  }

  abrirFormulario(solicitante?: Solicitante) {
    const ref = this.dialog.open(SolicitanteFormComponent, {
      width: '520px',
      data: solicitante ?? null
    });
    ref.afterClosed().subscribe(result => { if (result) this.cargar(); });
  }

  eliminar(id: number) {
    if (confirm('¿Eliminar este solicitante?')) {
      this.solicitanteService.eliminar(id).subscribe(() => this.cargar());
    }
  }
}