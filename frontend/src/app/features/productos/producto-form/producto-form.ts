import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatButtonModule } from '@angular/material/button';
import { ProductoService } from '../../../core/services/producto';
import { ProductoCredito } from '../../../core/models/producto';

@Component({
  selector: 'app-producto-form',
  standalone: true,
  imports: [ReactiveFormsModule, MatDialogModule, MatIconModule, MatSlideToggleModule, MatButtonModule],
  templateUrl: './producto-form.html',
  styleUrl: './producto-form.scss'
})
export class ProductoFormComponent implements OnInit {
  private fb = inject(FormBuilder);
  private service = inject(ProductoService);
  private dialogRef = inject(MatDialogRef<ProductoFormComponent>);
  data: ProductoCredito | null = inject(MAT_DIALOG_DATA);

  form!: FormGroup;
  esEdicion = false;

  ngOnInit() {
    this.esEdicion = !!this.data;
    this.form = this.fb.group({
      nombre: [this.data?.nombre ?? '', Validators.required],
      montoMin: [this.data?.montoMin ?? '', [Validators.required, Validators.min(1)]],
      montoMax: [this.data?.montoMax ?? '', [Validators.required, Validators.min(1)]],
      tasaInteres: [this.data?.tasaInteres ? this.data.tasaInteres * 100 : '', [Validators.required, Validators.min(0.1)]],
      plazoMaxMeses: [this.data?.plazoMaxMeses ?? '', [Validators.required, Validators.min(1)]],
      activo: [this.data?.activo ?? true]
    });
  }

  guardar() {
    if (this.form.invalid) return;
    const val = this.form.value;
    const producto: ProductoCredito = {
      ...val,
      tasaInteres: val.tasaInteres / 100
    };
    if (this.esEdicion) {
      this.service.actualizar(this.data!.id!, producto)
        .subscribe(() => this.dialogRef.close(true));
    } else {
      this.service.crear(producto)
        .subscribe(() => this.dialogRef.close(true));
    }
  }

  cancelar() { this.dialogRef.close(false); }
}