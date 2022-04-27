export class DeviceCommand {
  constructor(public id: string, public name: string, public payload: string, public ref: number, public port: number) {
  }

  static empty() {
    return new DeviceCommand('', '', '', 0, 1);
  }

  public clone() {
    return new DeviceCommand(this.id, this.name, this.payload, this.ref, this.port);
  }

  isValid() {
    return this.id.length !== 0 && this.name.length !== 0 && this.payload.length !== 0 && this.ref >= 0 && this.port > 0;
  }
}
