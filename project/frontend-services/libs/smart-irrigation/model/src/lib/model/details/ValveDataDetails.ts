import {GPSDataDetails} from "./GPSDataDetails";
import {ValveStatusDataDetails} from "./ValveStatusDataDetails";
import {SensorDataDetails} from "./SensorDataDetails";

export class ValveDataDetails extends SensorDataDetails {
  constructor(gps: GPSDataDetails, public valve: ValveStatusDataDetails) {
    super(gps, '#adb5bd');
  }
}
