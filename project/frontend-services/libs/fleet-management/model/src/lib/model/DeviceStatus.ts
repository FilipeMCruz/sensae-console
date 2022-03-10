export enum MotionType {
  ACTIVE = 1,
  INACTIVE = 2,
  UNKWOWN = 3,
}

export class DeviceStatus {
  constructor(public motion: MotionType) {}
}
