import {RecordEntry} from "./RecordEntry";
import {RecordType} from "./RecordType";

export class DeviceRecord {
  constructor(public deviceId: string, public entries: Array<RecordEntry>) {
  }

  public getNumberOfBasicEntries(): number {
    return this.entries.filter(a => a.type === RecordType.BASIC).length
  }

  public getNumberOfSensorDataEntries(): number {
    return this.entries.filter(a => a.type === RecordType.SENSOR_DATA).length
  }
}
