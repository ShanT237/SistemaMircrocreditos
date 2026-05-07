import { TestBed } from '@angular/core/testing';

import { Desembolso } from './desembolso';

describe('Desembolso', () => {
  let service: Desembolso;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Desembolso);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
