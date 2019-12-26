export interface ActionMode {
  readonly name: string;
  readonly ordinal: number;
  readonly displayName: string;
}

export namespace ActionMode {
  let ordinal = 0;

  function define(name: string, displayName: string): ActionMode {
    const it = {name, ordinal, displayName};
    ordinal += 1;
    return it;
  }

  export const AV = define('AV', 'A/V');
  export const AUDIO = define('AUDIO', 'Audio');
  export const VISUAL = define('VISUAL', 'Visual');
  export const NONE = define('NONE', 'None');
  const VALUES = Object.freeze([AV, AUDIO, VISUAL, NONE]);

  export function valueOf(name: string): ActionMode | undefined {
    return VALUES.find(it => it.name === name);
  }

  export function values(): ReadonlyArray<ActionMode> {
    return VALUES;
  }
}

