import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatIconModule } from '@angular/material/icon';
import { OperadorService } from '../../../core/services/operador';
import { Operador } from '../../../core/models/operador';
@Component({
  selector: 'app-operador-form',
  standalone: true,
  imports: [
  ReactiveFormsModule,
  MatDialogModule,
  MatButtonModule,
  MatSlideToggleModule,
  MatIconModule
],
  templateUrl: './operador-form.html',
  styleUrl: './operador-form.scss'
})
export class OperadorFormComponent implements OnInit {
  private fb = inject(FormBuilder);
  private operadorService = inject(OperadorService);
  private dialogRef = inject(MatDialogRef<OperadorFormComponent>);
  data: Operador | null = inject(MAT_DIALOG_DATA);

  form!: FormGroup;
  esEdicion = false;
  roles = ['VALORADOR', 'APROBADOR', 'ANALISTA'];

  ngOnInit() {
    this.esEdicion = !!this.data;
    this.form = this.fb.group({
      nombre: [this.data?.nombre ?? '', Validators.required],
      email: [this.data?.email ?? '', [Validators.required, Validators.email]],
      rol: [this.data?.rol ?? null, Validators.required],
      activo: [this.data?.activo ?? true]
    });
  }

  guardar() {
    if (this.form.invalid) return;
    const operador: Operador = this.form.value;

    if (this.esEdicion) {
      this.operadorService.actualizar(this.data!.id!, operador)
        .subscribe(() => this.dialogRef.close(true));
    } else {
      this.operadorService.crear(operador)
        .subscribe(() => this.dialogRef.close(true));
    }
  }

  cancelar() {
    this.dialogRef.close(false);
  }
}