import {DeviceId} from "./DeviceId";
import {GardeningAreaId} from "../garden/GardeningAreaId";

export class LatestDataQueryFilters {
  constructor(public devices: Array<DeviceId>, public gardens: Array<GardeningAreaId>) {
  }
}
