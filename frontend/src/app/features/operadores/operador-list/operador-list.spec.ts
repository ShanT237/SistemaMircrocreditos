import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OperadorList } from './operador-list';

describe('OperadorList', () => {
  let component: OperadorList;
  let fixture: ComponentFixture<OperadorList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OperadorList],
    }).compileComponents();

    fixture = TestBed.createComponent(OperadorList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
