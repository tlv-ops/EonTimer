import {ConsoleFamily} from './console-family';
import {Sound} from './sound';
import {ActionMode} from './action-mode';

export const MINIMUM_LENGTH = 14000;
export const UPDATE_FACTOR = 1.0;
export const CLOSE_UPDATE_FACTOR = 0.75;
export const CLOSE_THRESHOLD = 167;

export interface AppSettings {
  gen4: Gen4Timer;
  action: ActionSettings;
  timer: TimerSettings;
}

export interface Gen4Timer {
  calibratedDelay: number;
  calibratedSecond: number;
  targetDelay: number;
  targetSecond: number;
}

export interface ActionSettings {
  mode: ActionMode;
  sound: Sound;
  color: string;
  interval: number;
  count: number;
}

export interface TimerSettings {
  console: ConsoleFamily;
  isPrecisionCalibrationMode: boolean;
  refreshInterval: number;
}

export const appSettings = getAppSettings();

function getAppSettings(): AppSettings {
  const settings = localStorage.getItem('settings');
  if (settings != null) {
    return JSON.parse(settings);
  } else {
    return {
      gen4: {
        calibratedDelay: 500,
        calibratedSecond: 14,
        targetDelay: 600,
        targetSecond: 50
      },
      timer: {
        console: ConsoleFamily.NDS,
        isPrecisionCalibrationMode: false,
        refreshInterval: 8
      },
      action: {
        mode: ActionMode.AV,
        sound: Sound.BEEP,
        color: '#00ffff',
        interval: 500,
        count: 6
      }
    };
  }
}

// TODO: uncomment this for prod build
(() => {
  window.onbeforeunload = () => {
    localStorage.setItem('settings', JSON.stringify(appSettings));
  };
})();
