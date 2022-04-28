import {DeviceType} from "./DeviceType";
import {RecordEntry} from "./RecordEntry";
import {DeviceId} from "./DeviceId";
import {DeviceName} from "./DeviceName";

export class Device {
  constructor(public id: DeviceId, public type: DeviceType, public name: DeviceName, public records: Array<RecordEntry>, public remoteControl: boolean) {

  }
}
