import {DeviceCoordinates} from "../DeviceCoordinates";
import {DeviceStatus} from "../DeviceStatus";

export class DeviceHistoryStep {
  constructor(public gps: DeviceCoordinates, public status: DeviceStatus, public reportedAt: Date) {
  }
}
