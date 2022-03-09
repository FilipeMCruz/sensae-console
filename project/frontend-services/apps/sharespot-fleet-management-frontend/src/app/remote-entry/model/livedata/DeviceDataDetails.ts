import { DeviceCoordinates } from '../DeviceCoordinates';
import { DeviceStatus } from '../DeviceStatus';

export class DeviceDataDetails {
  constructor(public gps: DeviceCoordinates, public status: DeviceStatus) {}
}
