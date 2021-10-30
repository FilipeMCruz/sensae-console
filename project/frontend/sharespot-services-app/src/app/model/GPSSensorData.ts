import {SensorCoordinates} from "./SensorCoordinates";

export class GPSSensorData {
  constructor(public dataId: String, public deviceId: String, public reportedAt: Date, public coordinates: SensorCoordinates) {
  }
}
