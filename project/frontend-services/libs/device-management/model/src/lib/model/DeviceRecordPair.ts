import { DeviceInformation } from './DeviceInformation';

export class DeviceRecordPair {
  constructor(public fresh: DeviceInformation, public old: DeviceInformation) {}
}
