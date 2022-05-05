import {GPSDataDetails} from "./GPSDataDetails";

export class SensorDataDetails {
  constructor(public gps: GPSDataDetails, public color: string) {
  }

  getDataDetailsInHTML() {
    return "";
  }
}
