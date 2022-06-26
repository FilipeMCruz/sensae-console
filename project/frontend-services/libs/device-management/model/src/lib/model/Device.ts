export class Device {
  constructor(public id: string, public name: string, public downlink: string) {
  }

  static empty() {
    return new Device('', '', '');
  }

  clone() {
    return new Device(this.id, this.name, this.downlink);
  }

  same(device: Device) {
    return this.name == device.name && this.id == device.id;
  }
}
