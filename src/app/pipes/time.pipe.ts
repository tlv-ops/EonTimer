import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'time'
})
export class TimePipe implements PipeTransform {
  transform(value: number, ...args: any[]): any {
    const seconds = Math.floor(value / 1000);
    const milliseconds = value - (seconds * 1000);
    return `${seconds}:${this.pad(milliseconds, 3)}`;
  }

  private pad(n: number, width: number, z: string = '0'): string {
    const s = String(n);
    return s.length < width ?
      z.repeat(width - s.length) + s :
      s;
  }
}
