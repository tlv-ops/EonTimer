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

  private get console(): ConsoleFamily {
    return appSettings.timer.console;
  }
}
