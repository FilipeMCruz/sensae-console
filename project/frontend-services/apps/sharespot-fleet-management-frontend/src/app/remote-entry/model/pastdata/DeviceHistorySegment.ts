import {DeviceHistoryStep} from "./DeviceHistoryStep";

export enum DeviceHistorySegmentType {
  INACTIVE,
  ACTIVE,
  UNKNOWN_ACTIVE,
  UNKNOWN_INACTIVE
}

export class DeviceHistorySegment {
  constructor(public type: DeviceHistorySegmentType, public steps: Array<DeviceHistoryStep>) {
  }
}
