import {DeviceId} from "./DeviceId";
import {IrrigationZoneId} from "../garden/IrrigationZoneId";

export class DataFilters {
  constructor(public devices: Array<DeviceId>, public gardens: Array<IrrigationZoneId>, public content: string) {
  }
}
