import {DeviceId, GardeningAreaId} from "@frontend-services/smart-irrigation/model";

export class HistoryQueryFilters {
  constructor(public devices: Array<DeviceId>, public gardens: Array<GardeningAreaId>, public startTime: Date, public endTime: Date) {
  }
}
