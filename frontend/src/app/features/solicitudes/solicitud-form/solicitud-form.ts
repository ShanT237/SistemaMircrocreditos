import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { SolicitudService } from '../../../core/services/solicitud';
import { SolicitanteService } from '../../../core/services/solicitante';
import { ProductoService } from '../../../core/services/producto';
import { OperadorService } from '../../../core/services/operador';
import { Solicitante } from '../../../core/models/solicitante';
import { ProductoCredito } from '../../../core/models/producto';
import { Operador } from '../../../core/models/operador';

@Component({
  selector: 'app-solicitud-form',
  standalone: true,
  imports: [ReactiveFormsModule, MatDialogModule, MatIconModule, MatButtonModule, CommonModule],
  templateUrl: './solicitud-form.html',
  styleUrl: './solicitud-form.scss'
})
export class SolicitudFormComponent implements OnInit {
  private fb = inject(FormBuilder);
  private solicitudService = inject(SolicitudService);
  private solicitanteService = inject(SolicitanteService);
  private productoService = inject(ProductoService);
  private operadorService = inject(OperadorService);
  private dialogRef = inject(MatDialogRef<SolicitudFormComponent>);

  form!: FormGroup;
  solicitantes: Solicitante[] = [];
  productos: ProductoCredito[] = [];
  operadores: Operador[] = [];
  productoSeleccionado: ProductoCredito | null = null;

  ngOnInit() {
    this.form = this.fb.group({
      solicitanteId: [null, Validators.required],
      productoId: [null, Validators.required],
      operadorId: [null, Validators.required],
      monto: ['', [Validators.required, Validators.min(1)]],
      plazoMeses: ['', [Validators.required, Validators.min(1)]]
    });

    this.solicitanteService.listar().subscribe(d => this.solicitantes = d);
    this.productoService.listar(true).subscribe(d => this.productos = d);
    this.operadorService.listar().subscribe(d => this.operadores = d);

    this.form.get('productoId')?.valueChanges.subscribe(id => {
      this.productoSeleccionado = this.productos.find(p => p.id === +id) ?? null;
    });
  }

  guardar() {
    if (this.form.invalid) return;
    const { solicitanteId, productoId, operadorId, monto, plazoMeses } = this.form.value;
    this.solicitudService.crear(solicitanteId, productoId, operadorId, { monto, plazoMeses })
      .subscribe({
        next: () => this.dialogRef.close(true),
        error: (err) => alert(err?.error?.message ?? 'Error al crear la solicitud')
      });
  }

  cancelar() { this.dialogRef.close(false); }
}