import {Component, OnInit} from '@angular/core';
import {appSettings, TimerSettings} from '../../../models/settings';
import {ConsoleFamily} from '../../../models/console-family';
import {copy, copyOf} from '../../../functions/functions';

@Component({
  selector: 'app-timer-settings',
  templateUrl: './timer-settings.component.html',
  styleUrls: ['./timer-settings.component.scss']
})
export class TimerSettingsComponent implements OnInit {
  model: TimerSettings;

  constructor() {
    this.model = copyOf(appSettings.timer);
  }

  ngOnInit() {
  }

  save() {
    copy(this.model, appSettings.timer);
  }

  reset() {
    this.model = copyOf(appSettings.timer);
  }

  get consoles(): ReadonlyArray<ConsoleFamily> {
    return ConsoleFamily.values();
  }

  get console(): number {
    return this.model.console.ordinal;
  }

  set console(value: number) {
    this.model.console = ConsoleFamily.values()[value];
  }
}
