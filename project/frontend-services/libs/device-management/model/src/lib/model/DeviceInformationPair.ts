import { DeviceInformation } from './DeviceInformation';

export class DeviceInformationPair {
  constructor(public fresh: DeviceInformation, public old: DeviceInformation) {}
}
