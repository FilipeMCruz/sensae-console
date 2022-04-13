import {DeviceId} from "./DeviceId";
import {GardeningAreaId} from "../garden/GardeningAreaId";

export class DataFilters {
  constructor(public devices: Array<DeviceId>, public gardens: Array<GardeningAreaId>, public content: string) {
  }
}
