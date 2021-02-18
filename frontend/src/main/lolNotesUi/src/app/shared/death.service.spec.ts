import { TestBed } from '@angular/core/testing';

import { DeathService } from './death.service';

describe('DeathService', () => {
  let service: DeathService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DeathService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
