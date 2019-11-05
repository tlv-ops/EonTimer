import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {Gen4TimerComponent} from './components/gen4-timer/gen4-timer.component';
import {SettingsModalComponent} from './components/settings-modal/settings-modal.component';
import {FormsModule} from '@angular/forms';
import {TimerDisplayComponent} from './components/timer-display/timer-display.component';
import {Gen3TimerComponent} from './components/gen3-timer/gen3-timer.component';
import { TimePipe } from './pipes/time.pipe';

@NgModule({
  declarations: [
    AppComponent,
    Gen3TimerComponent,
    Gen4TimerComponent,
    TimerDisplayComponent,
    SettingsModalComponent,
    TimePipe
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
