import {Component, NgZone} from '@angular/core';
import {appSettings} from '../../models/settings';
import {TimerService} from '../../services/timer.service';
import {CalibratorService} from '../../services/calibrator.service';
import {DelayTimerService} from '../../services/timer/delay-timer.service';

@Component({
  selector: 'app-gen4-timer',
  templateUrl: './gen4-timer.component.html',
  styleUrls: ['./gen4-timer.component.scss']
})
export class Gen4TimerComponent {
  constructor(private timer: TimerService,
              private calibrator: CalibratorService,
              private delayTimer: DelayTimerService,
              private zone: NgZone) {
  }

  onClick() {
    if (!this.timer.isRunning) {
      this.timer.start();
    } else {
      this.timer.stop();
    }
  }

  updateTimer() {
    this.zone.run(() => {
      this.timer.stages = this.delayTimer.createStages(
        this.targetSecond, this.targetDelay, this.calibration);
    });
  }

  private get calibration(): number {
    return this.calibrator.createCalibration(this.calibratedDelay, this.calibratedSecond);
  }

  get calibratedDelay(): number {
    return appSettings.gen4.calibratedDelay;
  }

  set calibratedDelay(value: number) {
    appSettings.gen4.calibratedDelay = value;
  }

  get calibratedSecond(): number {
    return appSettings.gen4.calibratedSecond;
  }

  set calibratedSecond(value: number) {
    appSettings.gen4.calibratedSecond = value;
  }

  get targetDelay(): number {
    return appSettings.gen4.targetDelay;
  }

  set targetDelay(value: number) {
    appSettings.gen4.targetDelay = value;
  }

  get targetSecond(): number {
    return appSettings.gen4.targetSecond;
  }

  set targetSecond(value: number) {
    appSettings.gen4.targetSecond = value;
  }
}
