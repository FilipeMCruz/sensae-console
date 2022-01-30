import {DeviceHistorySegmentType} from "./DeviceHistorySegmentType";
import {DeviceHistoryStep} from "./DeviceHistoryStep";

export class DeviceHistorySegment {
  constructor(public type: DeviceHistorySegmentType, public steps: Array<DeviceHistoryStep>) {
  }
}
