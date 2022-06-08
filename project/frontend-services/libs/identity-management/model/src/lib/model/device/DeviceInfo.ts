import {DeviceDomainPermission} from './DeviceDomainPermission';

export class DeviceInfo {
  constructor(
    public id: string,
    public name: string,
    public domains: Array<DeviceDomainPermission>
  ) {
  }
}
