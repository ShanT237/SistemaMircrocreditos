import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog } from '@angular/material/dialog';
import { MatChipsModule } from '@angular/material/chips';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { CommonModule } from '@angular/common';
import { OperadorService } from '../../../core/services/operador';
import { Operador } from '../../../core/models/operador';
import { OperadorFormComponent } from '../operador-form/operador-form';


@Component({
  selector: 'app-operador-list',
  standalone: true,
  imports: [
    MatTableModule, MatButtonModule, MatIconModule,
    MatChipsModule, MatProgressSpinnerModule, CommonModule
  ],
  templateUrl: './operador-list.html',
  styleUrl: './operador-list.scss'
})
export class OperadorListComponent implements OnInit {
  operadores: Operador[] = [];
  columnas = ['nombre', 'email', 'rol', 'activo', 'acciones'];
  cargando = true;
  error = '';

  constructor(
    private operadorService: OperadorService,
    private dialog: MatDialog,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() { this.cargar(); }

  cargar() {
    this.cargando = true;
    this.error = '';
    this.operadorService.listar().subscribe({
      next: data => {
        this.operadores = data;
        this.cargando = false;
        this.cdr.detectChanges();
      },
      error: () => {
        this.error = 'No se pudo conectar al servidor.';
        this.cargando = false;
        this.cdr.detectChanges();
      }
    });
  }

  abrirFormulario(operador?: Operador) {
    const ref = this.dialog.open(OperadorFormComponent, {
      width: '480px',
      data: operador ?? null
    });
    ref.afterClosed().subscribe(result => {
      if (result) this.cargar();
    });
  }

  eliminar(id: number) {
    if (confirm('¿Eliminar este operador?')) {
      this.operadorService.eliminar(id).subscribe(() => this.cargar());
    }
  }
}