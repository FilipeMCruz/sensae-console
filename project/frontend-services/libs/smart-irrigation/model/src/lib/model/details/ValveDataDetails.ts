import {GPSDataDetails} from "./GPSDataDetails";
import {ValveStatusDataDetails} from "./ValveStatusDataDetails";
import {SensorDataDetails} from "./SensorDataDetails";

export class ValveDataDetails extends SensorDataDetails {
  constructor(gps: GPSDataDetails, public valve: ValveStatusDataDetails) {
    super(gps, '#adb5bd');
  }

  override getDataDetailsInHTML() {
    return `<strong>Device Type:</strong> Valve</br>
<strong>Valve Status:</strong> ${this.valve.status}
`
  }
}
