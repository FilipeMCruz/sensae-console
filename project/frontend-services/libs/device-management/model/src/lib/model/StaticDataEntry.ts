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
    return this.label !== SensorDataRecordLabel.ERROR &&
      this.content.trim().length !== 0 &&
      !isNaN(+this.content.trim());
  }

  clone() {
    return new StaticDataEntry(this.label, this.content);
  }

  getIcon() {
    switch (this.label) {
      case SensorDataRecordLabel.GPS_ALTITUDE:
        return 'terrain';
      case SensorDataRecordLabel.BATTERY_MIN_VOLTS:
        return 'battery_0_bar';
      case SensorDataRecordLabel.BATTERY_MAX_VOLTS:
        return 'battery_full';
      case SensorDataRecordLabel.MIN_DISTANCE:
        return 'straighten';
      case SensorDataRecordLabel.MAX_DISTANCE:
        return 'straighten';
      case SensorDataRecordLabel.GPS_LATITUDE:
        return 'share_location'
      case SensorDataRecordLabel.GPS_LONGITUDE:
        return 'share_location'
      case SensorDataRecordLabel.ERROR:
        return 'error'
    }
  }
}
