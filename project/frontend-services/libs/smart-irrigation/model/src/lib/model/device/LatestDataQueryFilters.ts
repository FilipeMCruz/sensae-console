import {DeviceId, GardeningAreaId} from "@frontend-services/smart-irrigation/model";

export class LatestDataQueryFilters {
  constructor(public devices: Array<DeviceId>, public gardens: Array<GardeningAreaId>) {
  }
}
