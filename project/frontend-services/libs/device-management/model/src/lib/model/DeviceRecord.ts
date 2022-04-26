import {RecordEntry} from './RecordEntry';
import {Device} from './Device';
import {SubDevice} from "./SubDevice";

export class DeviceRecord {
  constructor(public device: Device, public entries: Array<RecordEntry>, public subDevices: Array<SubDevice>) {
  }

  static empty() {
    return new DeviceRecord(Device.empty(), new Array<RecordEntry>(), new Array<SubDevice>());
  }

  isValid() {
    return this.entries.filter((e) => !e.isValid()).length == 0 &&
      this.device.id.trim().length != 0 && this.checkForDuplicatedSubDevices();
  }

  private checkForDuplicatedSubDevices(): boolean {
    const ids = this.subDevices.map(e => e.id).sort();
    for (let i = 0; i < ids.length; i++) {
      if (ids[i + 1] === ids[i]) {
        return false;
      }
    }
    const refs = this.subDevices.map(e => e.id).sort();
    for (let i = 0; i < refs.length; i++) {
      if (refs[i + 1] === refs[i]) {
        return false;
      }
    }
    return true;
  }
}
