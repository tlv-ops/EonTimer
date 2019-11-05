export interface ConsoleFamily {
  readonly name: string;
  readonly ordinal: number;
  readonly fps: number;
  readonly frameRate: number;
}

export namespace ConsoleFamily {
  let ordinal = 0;

  function define(name: string, fps: number): ConsoleFamily {
    const it = {name, ordinal, fps, frameRate: 1000 / fps};
    ordinal += 1;
    return it;
  }

  export const GBA = define('GBA', 59.7271);
  export const NDS = define('NDS', 59.8261);
  const VALUES = Object.freeze([GBA, NDS]);

  export function valueOf(name: string): ConsoleFamily | undefined {
    return VALUES.find(it => it.name === name);
  }

  export function values(): ReadonlyArray<ConsoleFamily> {
    return VALUES;
  }
}
