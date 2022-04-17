import {PropertyName} from './PropertyName';

export class PropertyTransformation {
  constructor(public oldPath: string, public newPath: PropertyName, public subSensorId: number) {
  }

  static empty() {
    return new PropertyTransformation('', PropertyName.INVALID, 0);
  }

  isValid() {
    return (
      this.newPath !== PropertyName.INVALID && this.oldPath.trim().length !== 0
    );
  }

  getSubSensor() {
    return this.subSensorId === 0 ? "" : " for Sensor " + this.subSensorId;
  }
}
