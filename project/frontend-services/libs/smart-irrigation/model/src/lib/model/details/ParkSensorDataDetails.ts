import {GPSDataDetails} from "./GPSDataDetails";
import {SoilMoistureDataDetails} from "./SoilMoistureDataDetails";
import {IlluminanceDataDetails} from "./IlluminanceDataDetails";
import {SensorDataDetails} from "./SensorDataDetails";

export class ParkSensorDataDetails extends SensorDataDetails {
  constructor(gps: GPSDataDetails, public soilMoisture: SoilMoistureDataDetails, public illuminance: IlluminanceDataDetails) {
    super(gps);
  }
}
