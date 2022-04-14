import {SensorDataHistoryDetails} from "./SensorDataHistoryDetails";
import {TemperatureDataDetails} from "../details/TemperatureDataDetails";
import {HumidityDataDetails} from "../details/HumidityDataDetails";

export class StoveSensorDataHistoryDetails extends SensorDataHistoryDetails {
  constructor(id: string, reportedAt: Date, public temperature: TemperatureDataDetails, public humidity: HumidityDataDetails) {
    super(id, reportedAt)
  }
}
