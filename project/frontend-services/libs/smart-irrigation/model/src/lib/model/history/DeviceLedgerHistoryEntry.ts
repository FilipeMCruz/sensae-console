import {GPSDataDetails} from "../details/GPSDataDetails";
import {RecordEntry} from "../device/RecordEntry";
import {SensorDataHistoryDetails} from "./SensorDataHistoryDetails";

export class DeviceLedgerHistoryEntry {
  constructor(public name: string, public gps: GPSDataDetails, public records: Array<RecordEntry>, public data: Array<SensorDataHistoryDetails>) {
  }
}
