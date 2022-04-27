import {RecordEntry} from './RecordEntry';
import {Device} from './Device';
import {SubDevice} from "./SubDevice";
import {DeviceCommand} from "./DeviceCommand";

export class DeviceInformation {
  constructor(public device: Device, public entries: Array<RecordEntry>, public subDevices: Array<SubDevice>, public commands: Array<DeviceCommand>) {
  }

  static empty() {
    return new DeviceInformation(Device.empty(), new Array<RecordEntry>(), new Array<SubDevice>(), new Array<DeviceCommand>());
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