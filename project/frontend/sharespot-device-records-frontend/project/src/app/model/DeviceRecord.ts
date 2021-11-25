import {RecordEntry} from "./RecordEntry";
import {RecordType} from "./RecordType";
import {Device} from "./Device";

export class DeviceRecord {
  constructor(public device: Device, public entries: Array<RecordEntry>) {
  }

  public getNumberOfBasicEntries(): number {
    return this.entries.filter(a => a.type === RecordType.BASIC).length
  }

  public getNumberOfSensorDataEntries(): number {
    return this.entries.filter(a => a.type === RecordType.SENSOR_DATA).length
  }
}
