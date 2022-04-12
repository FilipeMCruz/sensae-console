import {SensorDataHistoryDetails} from "./SensorDataHistoryDetails";
import {SoilMoistureDataDetails} from "../details/SoilMoistureDataDetails";
import {IlluminanceDataDetails} from "../details/IlluminanceDataDetails";

export class ParkSensorDataHistoryDetails extends SensorDataHistoryDetails {
  constructor(id: string, reportedAt: Date, public soilMoisture: SoilMoistureDataDetails, public illuminance: IlluminanceDataDetails) {
    super(id, reportedAt)
  }
}
