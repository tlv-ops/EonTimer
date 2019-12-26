export type JQuery = (selector: string) => Selector;

export interface Selector {
  modal(options: ModalOptions | string);
}

export interface ModalOptions {
  backdrop?: boolean;
  keyboard?: boolean;
  focus?: boolean;
  show?: boolean;
}
