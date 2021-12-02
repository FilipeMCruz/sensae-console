import {RecordType} from "./RecordType";
import {SensorDataRecordLabel} from "./SensorDataRecordLabel";

export class RecordEntry {
  constructor(public label: string | SensorDataRecordLabel, public content: string, public type: RecordType) {
  }
}
