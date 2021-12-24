import {PropertyTransformation} from "./PropertyTransformation";
import {SensorTypeId} from "./SensorTypeId";

export class DataTransformation {
  constructor(public data: SensorTypeId, public entries: Array<PropertyTransformation>) {
  }

  static empty() {
    return new DataTransformation(SensorTypeId.empty(), new Array<PropertyTransformation>());
  }

  isValid() {
    return this.data.type.trim().length !== 0 && this.entries.filter(e => !e.isValid()).length == 0
  }
}
