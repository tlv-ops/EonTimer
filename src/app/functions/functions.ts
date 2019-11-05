import {MINIMUM_LENGTH} from '../models/settings';

export function toMinimumLength(value: number): number {
  let normalized = value;
  while (normalized < MINIMUM_LENGTH) {
    normalized += 60000;
  }
  return normalized;
}
