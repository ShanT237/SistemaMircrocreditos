import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatButtonModule } from '@angular/material/button';
import { SolicitanteService } from '../../../core/services/solicitante';
import { Solicitante } from '../../../core/models/solicitante';

@Component({
  selector: 'app-solicitante-form',
  standalone: true,
  imports: [ReactiveFormsModule, MatDialogModule, MatIconModule, MatSlideToggleModule, MatButtonModule],
  templateUrl: './solicitante-form.html',
  styleUrl: './solicitante-form.scss'
})
export class SolicitanteFormComponent implements OnInit {
  private fb = inject(FormBuilder);
  private service = inject(SolicitanteService);
  private dialogRef = inject(MatDialogRef<SolicitanteFormComponent>);
  data: Solicitante | null = inject(MAT_DIALOG_DATA);

  form!: FormGroup;
  esEdicion = false;

  ngOnInit() {
    this.esEdicion = !!this.data;
    this.form = this.fb.group({
      cedula: [{ value: this.data?.cedula ?? '', disabled: this.esEdicion }, Validators.required],
      nombre: [this.data?.nombre ?? '', Validators.required],
      email: [this.data?.email ?? '', [Validators.required, Validators.email]],
      telefono: [this.data?.telefono ?? '', Validators.required],
      ingresos: [this.data?.ingresos ?? '', [Validators.required, Validators.min(1)]],
      estado: [this.data?.estado ?? 'ACTIVO']
    });
  }

  guardar() {
    if (this.form.invalid) return;
    const solicitante: Solicitante = this.form.getRawValue();
    if (this.esEdicion) {
      this.service.actualizar(this.data!.id!, solicitante)
        .subscribe(() => this.dialogRef.close(true));
    } else {
      this.service.crear(solicitante)
        .subscribe(() => this.dialogRef.close(true));
    }
  }

  cancelar() { this.dialogRef.close(false); }
}