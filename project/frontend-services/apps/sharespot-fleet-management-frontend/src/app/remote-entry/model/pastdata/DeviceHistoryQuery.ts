import {Device} from "../Device";

export class DeviceHistoryQuery {
  constructor(public devices: Array<Device>,
              public startTime: Date,
              public endTime: Date) {
  }
}
