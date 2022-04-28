export class SubDevice {
  constructor(public reference: number, public id: string) {
  }

  static empty(): SubDevice {
    return new SubDevice(0, '');
  }

  public clone() {
    return new SubDevice(this.reference, this.id);
  }

  isValid(): boolean {
    if (this.reference == 0) {
      return false;
    } else {
      return this.id.length !== 0;
    }
  }
}
