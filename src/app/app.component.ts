import {Component, ViewChild} from '@angular/core';
import {SettingsModalComponent} from './components/settings-modal/settings-modal.component';
import {TimerService} from './services/timer.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'eon-timer';

  @ViewChild(SettingsModalComponent, {static: false})
  settingsModal: SettingsModalComponent;

  constructor(public timer: TimerService) {
  }
}
