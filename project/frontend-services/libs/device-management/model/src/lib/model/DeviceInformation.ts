import {RecordEntry} from './RecordEntry';
import {Device} from './Device';
import {SubDevice} from "./SubDevice";
import {DeviceCommand} from "./DeviceCommand";
import {StaticDataEntry} from "./StaticDataEntry";

export class DeviceInformation {
  constructor(public device: Device, public records: Array<RecordEntry>, public staticData: Array<StaticDataEntry>, public subDevices: Array<SubDevice>, public commands: Array<DeviceCommand>) {
  }

  static empty() {
    return new DeviceInformation(Device.empty(), new Array<RecordEntry>(), new Array<StaticDataEntry>(), new Array<SubDevice>(), new Array<DeviceCommand>());
  }

  clone() {
    return new DeviceInformation(this.device.clone(), this.records.map(r => r.clone()), this.staticData.map(s => s.clone()), this.subDevices.map(s => s.clone()), this.commands.map(c => c.clone()));
  }

  isValid() {
    return this.records.filter((e) => !e.isValid()).length == 0 &&
      this.staticData.filter((e) => !e.isValid()).length == 0 &&
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
