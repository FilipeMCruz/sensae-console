export class IsDraft {
  constructor(public value: boolean) {
  }

  static default() {
    return new IsDraft(false);
  }
}
