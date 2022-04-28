import {RecordEntryType} from './RecordEntryType';
import {SensorDataRecordLabel} from './SensorDataRecordLabel';

export class RecordEntry {
  constructor(
    public label: string | SensorDataRecordLabel,
    public content: string,
    public type: RecordEntryType
  ) {
  }

  static empty() {
    return new RecordEntry('', '', RecordEntryType.BASIC);
  }

  clone(): RecordEntry {
    return new RecordEntry(this.label, this.content, this.type);
  }

  isValid(): boolean {
    if (this.type == RecordEntryType.BASIC) {
      return true;
    } else {
      return Object.values(SensorDataRecordLabel).includes(
        this.label as SensorDataRecordLabel
      );
    }
  }
}
