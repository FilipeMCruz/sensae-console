export class Device {
  constructor(public id: string, public name: string) {
  }

  static empty() {
    return new Device('', '');
  }

  same(device: Device) {
    return this.name == device.name && this.id == device.id;
  }
}