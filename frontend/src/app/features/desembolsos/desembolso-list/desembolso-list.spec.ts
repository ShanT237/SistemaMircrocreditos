import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DesembolsoList } from './desembolso-list';

describe('DesembolsoList', () => {
  let component: DesembolsoList;
  let fixture: ComponentFixture<DesembolsoList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DesembolsoList],
    }).compileComponents();

    fixture = TestBed.createComponent(DesembolsoList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
