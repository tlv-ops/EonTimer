import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {ActionSettingsComponent} from './action-settings/action-settings.component';
import {JQuery, Selector} from '../../functions/jquery';
import {TimerSettingsComponent} from './timer-settings/timer-settings.component';

declare const $: JQuery;

@Component({
  selector: 'app-settings-modal',
  templateUrl: './settings-modal.component.html',
  styleUrls: ['./settings-modal.component.scss']
})
export class SettingsModalComponent implements AfterViewInit {
  private modal: Selector;
  @ViewChild(ActionSettingsComponent, {static: false})
  actionSettings: ActionSettingsComponent;
  @ViewChild(TimerSettingsComponent, {static: false})
  timerSettings: TimerSettingsComponent;

  constructor() {
  }

  ngAfterViewInit(): void {
    this.modal = $('#settingsModal');
  }

  show() {
    this.modal.modal('show');
  }

  hide() {
    this.modal.modal('hide');
  }

  save() {
    this.actionSettings.save();
    this.timerSettings.save();
    this.hide();
  }

  cancel() {
    this.actionSettings.reset();
    this.timerSettings.reset();
    this.hide();
  }
}
