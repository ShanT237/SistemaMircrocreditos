import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SolicitanteList } from './solicitante-list';

describe('SolicitanteList', () => {
  let component: SolicitanteList;
  let fixture: ComponentFixture<SolicitanteList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SolicitanteList],
    }).compileComponents();

    fixture = TestBed.createComponent(SolicitanteList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
