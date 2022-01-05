import {RecordEntry} from "./RecordEntry";

export class Device {
  constructor(public id: string, public name: string, public records: Array<RecordEntry>) {
  }

  has(deviceId: string) {
    return deviceId.trim() === this.id || deviceId.trim() === this.name;
  }
}
