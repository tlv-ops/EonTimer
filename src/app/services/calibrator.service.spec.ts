import { TestBed } from '@angular/core/testing';

import { CalibratorService } from './calibrator.service';

describe('CalibratorService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CalibratorService = TestBed.get(CalibratorService);
    expect(service).toBeTruthy();
  });
});
