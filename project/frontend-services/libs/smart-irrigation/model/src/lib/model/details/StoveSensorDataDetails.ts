import {GPSDataDetails} from "./GPSDataDetails";
import {HumidityDataDetails} from "./HumidityDataDetails";
import {TemperatureDataDetails} from "./TemperatureDataDetails";
import {SensorDataDetails} from "./SensorDataDetails";

export class StoveSensorDataDetails extends SensorDataDetails {
  constructor(gps: GPSDataDetails, public temperature: TemperatureDataDetails, public humidity: HumidityDataDetails) {
    super(gps, '#a68a64');
  }

  override getDataDetailsInHTML() {
    return `<strong>Device Type:</strong> Sensor</br>
<strong>Temperature:</strong> ${this.temperature.celsius} ºC</br>
<strong>Air Humidity:</strong> ${this.humidity.relativePercentage} %
`
  }
}
