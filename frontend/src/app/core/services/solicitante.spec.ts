import { TestBed } from '@angular/core/testing';

import { Solicitante } from './solicitante';

describe('Solicitante', () => {
  let service: Solicitante;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Solicitante);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
