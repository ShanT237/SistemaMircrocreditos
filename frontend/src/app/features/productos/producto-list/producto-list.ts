import { Component, OnInit, ChangeDetectorRef, inject } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog } from '@angular/material/dialog';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { CommonModule } from '@angular/common';
import { ProductoService } from '../../../core/services/producto';
import { ProductoCredito } from '../../../core/models/producto';
import { ProductoFormComponent } from '../producto-form/producto-form';

@Component({
  selector: 'app-producto-list',
  standalone: true,
  imports: [MatTableModule, MatButtonModule, MatIconModule, MatProgressSpinnerModule, CommonModule],
  templateUrl: './producto-list.html',
  styleUrl: './producto-list.scss'
})
export class ProductoListComponent implements OnInit {
  productos: ProductoCredito[] = [];
  columnas = ['nombre', 'montoMin', 'montoMax', 'tasaInteres', 'plazoMaxMeses', 'activo', 'acciones'];
  cargando = true;
  error = '';

  private productoService = inject(ProductoService);
  private dialog = inject(MatDialog);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit() { this.cargar(); }

  cargar() {
    this.cargando = true;
    this.error = '';
    this.productoService.listar(false).subscribe({
      next: data => { this.productos = data; this.cargando = false; this.cdr.detectChanges(); },
      error: () => { this.error = 'No se pudo conectar al servidor.'; this.cargando = false; this.cdr.detectChanges(); }
    });
  }

  abrirFormulario(producto?: ProductoCredito) {
    const ref = this.dialog.open(ProductoFormComponent, {
      width: '520px',
      data: producto ?? null
    });
    ref.afterClosed().subscribe(result => { if (result) this.cargar(); });
  }
}