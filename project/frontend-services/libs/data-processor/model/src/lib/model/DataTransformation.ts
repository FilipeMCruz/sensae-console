import {PropertyTransformation} from './PropertyTransformation';
import {SensorTypeId} from './SensorTypeId';

export class DataTransformation {
  constructor(
    public data: SensorTypeId,
    public entries: Array<PropertyTransformation>,
    public lastTimeSeen: Date
  ) {
  }

  static empty() {
    const d = new Date(0); // The 0 there is the key, which sets the date to the epoch
    d.setUTCMilliseconds(0);
    return new DataTransformation(
      SensorTypeId.empty(),
      new Array<PropertyTransformation>(),
      d
    );
  }

  isValid() {
    return (
      this.data.isValid() &&
      this.entries.filter((e) => !e.isValid()).length == 0
    );
  }
}
