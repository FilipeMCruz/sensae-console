import {SensorDataRecordLabel} from "./SensorDataRecordLabel";

export class StaticDataEntry {
  constructor(
    public label: SensorDataRecordLabel,
    public content: string,
  ) {
  }

  static empty() {
    return new StaticDataEntry(SensorDataRecordLabel.ERROR, '');
  }

  copy(): StaticDataEntry {
    return new StaticDataEntry(this.label, this.content);
  }

  isValid(): boolean {
    return this.content !== SensorDataRecordLabel.ERROR;
  }

  clone() {
    return new StaticDataEntry(this.label, this.content);
  }

  getIcon() {
    switch (this.label) {
      case SensorDataRecordLabel.ERROR:
        return 'error'
      case SensorDataRecordLabel.GPS_LATITUDE:
        return 'share_location'
      case SensorDataRecordLabel.GPS_LONGITUDE:
        return 'share_location'
    }
  }
}
