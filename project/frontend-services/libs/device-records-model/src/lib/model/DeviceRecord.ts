import { RecordEntry } from './RecordEntry';
import { Device } from './Device';

export class DeviceRecord {
  constructor(public device: Device, public entries: Array<RecordEntry>) {}

  static empty() {
    return new DeviceRecord(Device.empty(), new Array<RecordEntry>());
  }

  isValid() {
    return (
      this.entries.filter((e) => !e.isValid()).length == 0 &&
      this.device.id.trim().length != 0
    );
  }
}
