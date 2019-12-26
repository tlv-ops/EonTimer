import {MINIMUM_LENGTH} from '../models/settings';
import {isNullOrUndefined} from 'util';

const ONE_MINUTE = 60000;

export function toMinimumLength(value: number): number {
  while (value < MINIMUM_LENGTH) {
    value += ONE_MINUTE;
  }
  return value;
}

export function copy(source: any, target: any) {
  for (const key of Object.keys(source)) {
    if (source.hasOwnProperty(key)) {
      const value = source[key];
      if (!isNullOrUndefined(value)) {
        target[key] = value;
      }
    }
  }
}

export function copyOf<T>(value: T): T {
  const clone = {} as T;
  Object.assign(clone, value);
  return clone;
}
