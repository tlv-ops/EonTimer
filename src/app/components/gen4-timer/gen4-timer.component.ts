import {Component, NgZone, OnInit} from '@angular/core';
import {appSettings, Gen4Timer} from '../../models/settings';
import {TimerService} from '../../services/timer.service';
import {CalibratorService} from '../../services/calibrator.service';
import {DelayTimerService} from '../../services/timer/delay-timer.service';

@Component({
  selector: 'app-gen4-timer',
  templateUrl: './gen4-timer.component.html',
  styleUrls: ['./gen4-timer.component.scss']
})
export class Gen4TimerComponent implements OnInit {
  delayHit: number;

  constructor(public timer: TimerService,
              public calibrator: CalibratorService,
              public delayTimer: DelayTimerService,
              public zone: NgZone) {
  }

  ngOnInit(): void {
    this.updateTimer();
  }

  timerBtnClicked() {
    if (!this.timer.isRunning) {
      this.timer.start();
    } else {
      this.timer.stop();
    }
  }

  updateBtnClicked() {
    if (!this.timer.isRunning) {
      this.model.calibratedDelay +=
        this.calibrator.calibrateToDelays(
          this.delayTimer.calibrate(
            this.model.targetDelay,
            this.delayHit));
      this.delayHit = undefined;
    }
  }

  updateTimer() {
    this.zone.run(() => {
      this.timer.stages = this.delayTimer.createStages(
        this.model.targetSecond, this.model.targetDelay, this.calibration);
    });
  }

  get model(): Gen4Timer {
    return appSettings.gen4;
  }

  get canUpdate(): boolean {
    const delayHit = this.delayHit === undefined ? '' : String(this.delayHit);
    return !this.timer.isRunning && delayHit.trim() !== '';
  }

  get calibration(): number {
    return this.calibrator.createCalibration(this.model.calibratedDelay, this.model.calibratedSecond);
  }
}
