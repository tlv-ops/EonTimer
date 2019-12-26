import { TestBed } from '@angular/core/testing';

import { TimerActionService } from './timer-action.service';

describe('TimerActionService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: TimerActionService = TestBed.get(TimerActionService);
    expect(service).toBeTruthy();
  });
});
