import {Injectable} from '@angular/core';
import {Sound} from '../models/sound';
import {appSettings} from '../models/settings';
import {ActionMode} from '../models/action-mode';

@Injectable({
  providedIn: 'root'
})
export class TimerActionService {
  private mIsInvoked = false;
  private readonly sounds: Map<number, HTMLAudioElement>;

  constructor() {
    this.sounds = new Map<number, HTMLAudioElement>();
    for (const sound of Sound.values()) {
      this.sounds.set(sound.ordinal, new Audio(sound.path));
    }
  }

  invoke() {
    if (this.isAudio) {
      this.sounds.get(this.sound).play();
    }
    if (this.isVisual) {
      this.mIsInvoked = true;
      window.setTimeout(() =>
        this.mIsInvoked = false, 75);
    }
  }

  get isInvoked(): boolean {
    return this.mIsInvoked;
  }

  private get mode(): ActionMode {
    return appSettings.action.mode;
  }

  private get sound(): number {
    return appSettings.action.sound.ordinal;
  }

  private get isAudio(): boolean {
    return this.mode.ordinal === ActionMode.AV.ordinal
      || this.mode.ordinal === ActionMode.AUDIO.ordinal;
  }

  private get isVisual(): boolean {
    return this.mode.ordinal === ActionMode.AV.ordinal
      || this.mode.ordinal === ActionMode.VISUAL.ordinal;
  }
}
