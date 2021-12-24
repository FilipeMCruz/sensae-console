export class SensorTypeId {
  constructor(public type: string) {
  }

  static empty() {
    return new SensorTypeId('');
  }
}
