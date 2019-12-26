import {Injectable} from '@angular/core';
import {ConsoleFamily} from '../models/console-family';
import {appSettings} from '../models/settings';

@Injectable({
  providedIn: 'root'
})
export class CalibratorService {
  toMillis(delays: number): number {
    return Math.round(delays * this.console.frameRate);
  }

  toDelays(millis: number): number {
    return Math.round(millis / this.console.frameRate);
  }

  createCalibration(delay: number, second: number): number {
    return this.toMillis(delay - this.toDelays(second * 1000));
  }

  calibrateToMillis(value: number): number {
    if (!this.isPrecisionCalibrationMode) {
      return this.toMillis(value);
    } else {
      return value;
    }
  }

  calibrateToDelays(value: number): number {
    if (!this.isPrecisionCalibrationMode) {
      return this.toDelays(value);
    } else {
      return value;
    }
  }

  private get console(): ConsoleFamily {
    return appSettings.timer.console;
  }

  private get isPrecisionCalibrationMode(): boolean {
    return appSettings.timer.isPrecisionCalibrationMode;
  }
}
