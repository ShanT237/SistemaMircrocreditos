import { Component, OnInit, ChangeDetectorRef, inject } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog } from '@angular/material/dialog';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { CommonModule } from '@angular/common';
import { SolicitudService } from '../../../core/services/solicitud';
import { Solicitud } from '../../../core/models/solicitud';
import { SolicitudFormComponent } from '../solicitud-form/solicitud-form';

@Component({
  selector: 'app-solicitud-list',
  standalone: true,
  imports: [MatTableModule, MatButtonModule, MatIconModule, MatProgressSpinnerModule, CommonModule],
  templateUrl: './solicitud-list.html',
  styleUrl: './solicitud-list.scss'
})
export class SolicitudListComponent implements OnInit {
  solicitudes: Solicitud[] = [];
  columnas = ['solicitante', 'producto', 'monto', 'plazoMeses', 'estado', 'fecha', 'acciones'];
  cargando = true;
  error = '';

  private solicitudService = inject(SolicitudService);
  private dialog = inject(MatDialog);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit() { this.cargar(); }

  cargar() {
    this.cargando = true;
    this.error = '';
    this.solicitudService.listar().subscribe({
      next: data => { this.solicitudes = data; this.cargando = false; this.cdr.detectChanges(); },
      error: () => { this.error = 'No se pudo conectar al servidor.'; this.cargando = false; this.cdr.detectChanges(); }
    });
  }

  abrirFormulario() {
    const ref = this.dialog.open(SolicitudFormComponent, { width: '560px', data: null });
    ref.afterClosed().subscribe(result => { if (result) this.cargar(); });
  }

  aprobar(id: number) {
    if (confirm('¿Aprobar esta solicitud?')) {
      this.solicitudService.aprobar(id).subscribe(() => this.cargar());
    }
  }

  rechazar(id: number) {
    if (confirm('¿Rechazar esta solicitud?')) {
      this.solicitudService.rechazar(id).subscribe(() => this.cargar());
    }
  }

  getEstadoClass(estado: string): string {
    const map: Record<string, string> = {
      PENDIENTE: 'badge-pendiente',
      APROBADA: 'badge-aprobada',
      RECHAZADA: 'badge-rechazada',
      CANCELADA: 'badge-cancelada'
    };
    return map[estado] ?? '';
  }
}