import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { DesembolsoService } from '../../../core/services/desembolso';
import { SolicitudService } from '../../../core/services/solicitud';
import { OperadorService } from '../../../core/services/operador';
import { Solicitud } from '../../../core/models/solicitud';
import { Operador } from '../../../core/models/operador';

@Component({
  selector: 'app-desembolso-form',
  standalone: true,
  imports: [ReactiveFormsModule, MatDialogModule, MatIconModule, MatButtonModule, CommonModule],
  templateUrl: './desembolso-form.html',
  styleUrl: './desembolso-form.scss'
})
export class DesembolsoFormComponent implements OnInit {
  private fb = inject(FormBuilder);
  private desembolsoService = inject(DesembolsoService);
  private solicitudService = inject(SolicitudService);
  private operadorService = inject(OperadorService);
  private dialogRef = inject(MatDialogRef<DesembolsoFormComponent>);

  form!: FormGroup;
  solicitudes: Solicitud[] = [];
  operadores: Operador[] = [];

  ngOnInit() {
    this.form = this.fb.group({
      solicitudId: [null, Validators.required],
      operadorId: [null, Validators.required],
      metodoPago: ['TRANSFERENCIA', Validators.required]
    });
    this.solicitudService.listar().subscribe(d =>
      this.solicitudes = d.filter(s => s.estado === 'APROBADA')
    );
    this.operadorService.listar().subscribe(d => this.operadores = d);
  }

  guardar() {
    if (this.form.invalid) return;
    const { solicitudId, operadorId, metodoPago } = this.form.value;
    this.desembolsoService.registrar(solicitudId, operadorId, { monto: 0, metodoPago })
      .subscribe({
        next: () => this.dialogRef.close(true),
        error: (err) => alert(err?.error?.message ?? 'Error al registrar el desembolso')
      });
  }

  cancelar() { this.dialogRef.close(false); }
}