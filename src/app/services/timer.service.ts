import {Injectable} from '@angular/core';
import {appSettings} from '../models/settings';

@Injectable({
  providedIn: 'root'
})
export class TimerService {
  private mIsRunning = false;
  private mTotalElapsed = 0;
  private mTotalDuration = 0;
  private mCurrentElapsed = 0;
  private mCurrentDuration = 0;
  private mNextDuration = 0;

  private stageIndex = 0;
  private mStages: ReadonlyArray<number> = [];

  start() {
    if (!this.mIsRunning) {
      this.mIsRunning = true;

      let lastTimestamp = Date.now();
      const run = (refreshInterval: number) => {
        window.setTimeout(() => {
          if (!this.isRunning) {
            this.stop();
            return;
          }
          const now = Date.now();
          const delta = now - lastTimestamp;
          this.mCurrentElapsed += delta;
          this.mTotalElapsed += delta;
          lastTimestamp = now;

          if (this.currentElapsed < this.currentDuration) {
            let nextRefreshInterval = this.refreshInterval;
            if (nextRefreshInterval + this.currentElapsed > this.currentDuration) {
              nextRefreshInterval = this.currentDuration - this.currentElapsed;
            }
            run(nextRefreshInterval);
          } else {
            if (this.mTotalElapsed >= this.mTotalDuration) {
              this.stop();
              return;
            }

            this.stageIndex += 1;
            // adjust for overflow
            this.mCurrentElapsed = this.currentElapsed - this.currentDuration;
            this.mCurrentDuration = this.getStage(this.stageIndex);
            this.mNextDuration = this.getStage(this.stageIndex + 1);
            run(appSettings.timer.refreshInterval);
          }
        }, refreshInterval);
      };
      run(appSettings.timer.refreshInterval);
    }
  }

  stop() {
    if (this.mIsRunning) {
      this.mIsRunning = false;
      this.mTotalElapsed = 0;
      this.mCurrentElapsed = 0;
      this.mCurrentDuration = this.getStage(0);
      this.mNextDuration = this.getStage(1);
      this.stageIndex = 0;
    }
  }

  private get refreshInterval(): number {
    return appSettings.timer.refreshInterval;
  }

  get stages(): ReadonlyArray<number> {
    return this.mStages;
  }

  set stages(stages: ReadonlyArray<number>) {
    if (!this.mIsRunning) {
      this.mStages = stages;
      this.mTotalDuration = stages.reduce((a, b) => a + b, 0);
      this.mCurrentDuration = this.getStage(0);
      this.mNextDuration = this.getStage(1);
    }
  }

  get totalElapsed(): number {
    return this.mTotalElapsed;
  }

  get totalDuration(): number {
    return this.mTotalDuration;
  }

  get totalRemaining(): number {
    return this.mTotalDuration - this.mTotalElapsed;
  }

  get currentElapsed(): number {
    return this.mCurrentElapsed;
  }

  get currentDuration(): number {
    return this.mCurrentDuration;
  }

  get currentRemaining(): number {
    return this.mCurrentDuration - this.mCurrentElapsed;
  }

  get nextDuration(): number {
    return this.mNextDuration;
  }

  get isRunning(): boolean {
    return this.mIsRunning;
  }

  private getStage(index: number): number {
    if (index < this.mStages.length) {
      return this.mStages[index];
    } else {
      return 0;
    }
  }
}
