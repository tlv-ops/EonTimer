import {Component} from '@angular/core';
import {TimerService} from '../../services/timer.service';

@Component({
  selector: 'app-timer-display',
  templateUrl: './timer-display.component.html',
  styleUrls: ['./timer-display.component.scss']
})
export class TimerDisplayComponent {
  constructor(protected timer: TimerService) {
  }

  protected get minutesBeforeTarget(): number {
    return Math.floor((this.timer.totalDuration - this.timer.totalElapsed) / 60000);
  }
}
