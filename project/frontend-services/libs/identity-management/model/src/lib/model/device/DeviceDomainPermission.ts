import {DevicePermissionType} from "./DevicePermissionType";

export class DeviceDomainPermission {
  constructor(
    public domainId: string,
    public permission: DevicePermissionType
  ) {
  }
}
