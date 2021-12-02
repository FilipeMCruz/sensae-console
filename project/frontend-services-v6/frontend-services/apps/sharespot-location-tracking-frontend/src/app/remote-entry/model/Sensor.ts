export class Sensor {
  constructor(public id: string, public name: string) {
  }

  has(deviceId: string) {
    return deviceId.trim() === this.id || deviceId.trim() === this.name;
  }
}
