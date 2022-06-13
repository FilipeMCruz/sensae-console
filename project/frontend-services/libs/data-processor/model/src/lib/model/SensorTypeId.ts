export class SensorTypeId {
  constructor(public type: string) {
  }

  static empty() {
    return new SensorTypeId('');
  }

  isValid() {
    return this.type != null && this.type.length !== 0 && this.type.match("^[A-Za-z0-9]+$");
  }
}
