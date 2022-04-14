import {SensorDataHistoryDetails} from "./SensorDataHistoryDetails";
import {ValveStatusDataDetails} from "../details/ValveStatusDataDetails";

export class ValveDataHistoryDetails extends SensorDataHistoryDetails {
  constructor(id: string, reportedAt: Date, public valve: ValveStatusDataDetails) {
    super(id, reportedAt)
  }
}
