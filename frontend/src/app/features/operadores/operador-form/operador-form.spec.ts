import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OperadorForm } from './operador-form';

describe('OperadorForm', () => {
  let component: OperadorForm;
  let fixture: ComponentFixture<OperadorForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OperadorForm],
    }).compileComponents();

    fixture = TestBed.createComponent(OperadorForm);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
