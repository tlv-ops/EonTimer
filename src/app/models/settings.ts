import {ConsoleFamily} from './console-family';

export const MINIMUM_LENGTH = 14000;
export const UPDATE_FACTOR = 1.0;
export const CLOSE_UPDATE_FACTOR = 0.75;
export const CLOSE_THRESHOLD = 167;

export interface Gen4Timer {
  calibratedDelay: number;
  calibratedSecond: number;
  targetDelay: number;
  targetSecond: number;
}

export interface ActionSettings {
  color: string;
  interval: number;
  count: number;
}

export interface TimerSettings {
  console: ConsoleFamily;
  refreshInterval: number;
}

export interface AppSettings {
  gen4: Gen4Timer;
  action: ActionSettings;
  timer: TimerSettings;
}

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
        refreshInterval: 8
      },
      action: {
        color: '#00ffff',
        interval: 500,
        count: 6
      }
    };
  }

}

export const appSettings = getAppSettings();

(() => {
  window.onbeforeunload = () => {
    localStorage.setItem('settings', JSON.stringify(appSettings));
  };
})();
