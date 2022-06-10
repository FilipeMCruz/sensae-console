import {IrrigationZoneId} from "../garden/IrrigationZoneId";
import {DeviceId} from "../device/DeviceId";

export class HistoryQueryFilters {
  constructor(public devices: Array<DeviceId>, public gardens: Array<IrrigationZoneId>, public startTime: Date, public endTime: Date) {
  }

  static defaultDevice(id: DeviceId): HistoryQueryFilters {
    const start = new Date();
    start.setMonth(start.getMonth() - 1);
    return new HistoryQueryFilters(Array.of(id), [], start, new Date());
  }
}
