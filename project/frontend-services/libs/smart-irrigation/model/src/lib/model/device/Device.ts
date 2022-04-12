import {DeviceType} from "./DeviceType";
import {RecordEntry} from "./RecordEntry";

export class Device {
  constructor(public id: string, public type: DeviceType, public name: string, public records: Array<RecordEntry>) {

  }
}
