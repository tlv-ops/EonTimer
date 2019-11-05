import { TestBed } from '@angular/core/testing';

import { SecondTimerService } from './second-timer.service';

describe('SecondTimerService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SecondTimerService = TestBed.get(SecondTimerService);
    expect(service).toBeTruthy();
  });
});
