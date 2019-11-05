import {Component, OnInit} from '@angular/core';

declare var $: any;

@Component({
  selector: 'app-settings-modal',
  templateUrl: './settings-modal.component.html',
  styleUrls: ['./settings-modal.component.scss']
})
export class SettingsModalComponent implements OnInit {

  constructor() {
  }

  ngOnInit() {
  }

  show() {
    $('#exampleModal').modal();
  }

}
