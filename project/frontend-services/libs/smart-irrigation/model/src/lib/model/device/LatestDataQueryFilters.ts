import {DeviceId} from "./DeviceId";
import {IrrigationZoneId} from "../garden/IrrigationZoneId";

export class LatestDataQueryFilters {
  constructor(public devices: Array<DeviceId>, public gardens: Array<IrrigationZoneId>) {
  }
}
