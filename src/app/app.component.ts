import {Component, ViewChild} from '@angular/core';
import {SettingsModalComponent} from './components/settings-modal/settings-modal.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'eon-timer';

  @ViewChild(SettingsModalComponent, {static: false})
  modal: SettingsModalComponent;

  onClick() {
    this.modal.show();
  }
}
