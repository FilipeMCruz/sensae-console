export class SubDevice {
  constructor(public reference: number, public id: string) {
  }

  static empty(): SubDevice {
    return new SubDevice(0, '');
  }

  isValid(): boolean {
    if (this.reference == 0) {
      return false;
    } else {
      return this.id.length !== 0;
    }
  }
}
