import {SensorDataHistoryDetails} from "./SensorDataHistoryDetails";
import {SoilMoistureDataDetails} from "../details/SoilMoistureDataDetails";
import {ValveStatusDataDetails} from "../details/ValveStatusDataDetails";

export class ValveDataHistoryDetails extends SensorDataHistoryDetails {
  constructor(id: string, reportedAt: Date, public soilMoisture: SoilMoistureDataDetails, public valve: ValveStatusDataDetails) {
    super(id, reportedAt)
  }
}
