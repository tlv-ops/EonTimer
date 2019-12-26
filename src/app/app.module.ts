import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {Gen4TimerComponent} from './components/gen4-timer/gen4-timer.component';
import {SettingsModalComponent} from './components/settings-modal/settings-modal.component';
import {FormsModule} from '@angular/forms';
import {TimerDisplayComponent} from './components/timer-display/timer-display.component';
import {TimePipe} from './pipes/time.pipe';
import {TimerSettingsComponent} from './components/settings-modal/timer-settings/timer-settings.component';
import {ActionSettingsComponent} from './components/settings-modal/action-settings/action-settings.component';

@NgModule({
  declarations: [
    AppComponent,
    Gen4TimerComponent,
    TimerDisplayComponent,
    SettingsModalComponent,
    TimePipe,
    TimerSettingsComponent,
    ActionSettingsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
