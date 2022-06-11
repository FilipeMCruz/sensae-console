import {GPSDataDetails} from "./GPSDataDetails";
import {SoilMoistureDataDetails} from "./SoilMoistureDataDetails";
import {IlluminanceDataDetails} from "./IlluminanceDataDetails";
import {SensorDataDetails} from "./SensorDataDetails";

export class ParkSensorDataDetails extends SensorDataDetails {
  constructor(gps: GPSDataDetails, public soilMoisture: SoilMoistureDataDetails, public illuminance: IlluminanceDataDetails) {
    super(gps, '#656d4a');
  }

  override getDataDetailsInHTML() {
    return `<strong>Device Type:</strong> Sensor</br>
<strong>Illuminance:</strong> ${this.illuminance.lux} lux</br>
<strong>Soil Moisture:</strong> ${this.soilMoisture.relativePercentage} %
`
  }
}
