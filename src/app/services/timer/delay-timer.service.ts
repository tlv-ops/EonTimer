import {Injectable} from '@angular/core';
import {SecondTimerService} from './second-timer.service';
import {CalibratorService} from '../calibrator.service';
import {toMinimumLength} from '../../functions/functions';
import {CLOSE_THRESHOLD, CLOSE_UPDATE_FACTOR, UPDATE_FACTOR} from '../../models/settings';

@Injectable({
  providedIn: 'root'
})
export class DelayTimerService {
  constructor(private secondTimer: SecondTimerService,
              private calibrator: CalibratorService) {
  }

  createStages(targetSecond: number, targetDelay: number, calibration: number): ReadonlyArray<number> {
    return [
      toMinimumLength(this.secondTimer.createStages(targetSecond, calibration)[0] - this.calibrator.toMillis(targetDelay)),
      this.calibrator.toMillis(targetDelay) - calibration
    ];
  }

  calibrate(targetDelay: number, delayHit: number): number {
    const delta = this.calibrator.toMillis(delayHit) - this.calibrator.toMillis(targetDelay);
    if (Math.abs(delta) <= CLOSE_THRESHOLD) {
      return CLOSE_UPDATE_FACTOR * delta;
    } else {
      return UPDATE_FACTOR * delta;
    }
  }
}
