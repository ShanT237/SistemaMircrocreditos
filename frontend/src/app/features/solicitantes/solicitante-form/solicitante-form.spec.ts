import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SolicitanteForm } from './solicitante-form';

describe('SolicitanteForm', () => {
  let component: SolicitanteForm;
  let fixture: ComponentFixture<SolicitanteForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SolicitanteForm],
    }).compileComponents();

    fixture = TestBed.createComponent(SolicitanteForm);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
