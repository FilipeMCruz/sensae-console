import {DeviceId, GardeningAreaId} from "@frontend-services/smart-irrigation/model";

export class DataFilters {
  constructor(public devices: Array<DeviceId>, public gardens: Array<GardeningAreaId>, public content: string) {
  }
}
