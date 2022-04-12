import {Device} from "./Device";
import {SensorDataDetails} from "../details/SensorDataDetails";

export class Data {
  constructor(public id: string, public device: Device, public reportedAt: Date, public data: SensorDataDetails) {
  }
}
