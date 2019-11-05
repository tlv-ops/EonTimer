export interface Sound {
  readonly name: string;
  readonly ordinal: number;
  readonly displayName: string;
  readonly path: string;
}

export namespace Sound {
  let ordinal = 0;

  function define(name: string, displayName: string, path: string): Sound {
    const it = {name, ordinal, displayName, path};
    ordinal += 1;
    return it;
  }

  export const BEEP = define('BEEP', 'Beep', 'assets/beep.wav');
  export const DING = define('DING', 'Ding', 'assets/ding.wav');
  export const TICK = define('TICK', 'Tick', 'assets/tick.wav');
  export const POP = define('POP', 'Pop', 'assets/pop.wav');
  const VALUES = Object.freeze([BEEP, DING, TICK, POP]);

  export function valueOf(name: string): Sound | undefined {
    return VALUES.find(it => it.name === name);
  }

  export function values(): ReadonlyArray<Sound> {
    return VALUES;
  }
}
