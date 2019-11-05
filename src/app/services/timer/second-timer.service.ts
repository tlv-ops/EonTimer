import {Injectable} from '@angular/core';
import {toMinimumLength} from '../../functions/functions';

@Injectable({
  providedIn: 'root'
})
export class SecondTimerService {
  createStages(targetSecond: number, calibration: number): ReadonlyArray<number> {
    return [toMinimumLength(targetSecond * 1000 + calibration + 200)];
  }

  calibrate(targetSecond: number, secondHit: number): number {
    if (secondHit < targetSecond) {
      return (targetSecond - secondHit) * 1000 - 500;
    } else if (secondHit > targetSecond) {
      return (targetSecond - secondHit) * 1000 + 500;
    } else {
      return 0;
    }
  }
}
