import {Component} from '@angular/core';
import {ActionMode} from '../../../models/action-mode';
import {ActionSettings, appSettings} from '../../../models/settings';
import {Sound} from '../../../models/sound';
import {copy, copyOf} from '../../../functions/functions';

@Component({
  selector: 'app-action-settings',
  templateUrl: './action-settings.component.html',
  styleUrls: ['./action-settings.component.scss']
})
export class ActionSettingsComponent {
  model: ActionSettings;

  constructor() {
    this.model = copyOf(appSettings.action);
  }

  save() {
    copy(this.model, appSettings.action);
  }

  reset() {
    this.model = copyOf(appSettings.action);
  }

  get actionModes(): ReadonlyArray<ActionMode> {
    return ActionMode.values();
  }

  get actionMode(): number {
    return this.model.mode.ordinal;
  }

  set actionMode(value: number) {
    this.model.mode = ActionMode.values()[value];
  }

  get sounds(): ReadonlyArray<Sound> {
    return Sound.values();
  }

  get sound(): number {
    return this.model.sound.ordinal;
  }

  set sound(value: number) {
    this.model.sound = Sound.values()[value];
  }
}
