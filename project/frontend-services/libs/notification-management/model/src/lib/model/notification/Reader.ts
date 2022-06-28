export class Reader {
  constructor(public oid: string, public name: string) {
  }

  static invalid() {
    return new Reader("invalid", "invalid");
  }
}
