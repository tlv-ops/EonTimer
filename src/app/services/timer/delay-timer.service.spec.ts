import { TestBed } from '@angular/core/testing';

import { DelayTimerService } from './delay-timer.service';

describe('DelayTimerService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: DelayTimerService = TestBed.get(DelayTimerService);
    expect(service).toBeTruthy();
  });
});
