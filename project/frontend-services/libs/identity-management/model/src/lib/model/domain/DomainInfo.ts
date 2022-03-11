import { Domain } from './Domain';
import { TenantInfo } from '../tenant/TenantInfo';
import { DeviceInfo } from '../device/DeviceInfo';

export class DomainInfo {
  constructor(
    public domain: Domain,
    public tenants: Array<TenantInfo>,
    public devices: Array<DeviceInfo>
  ) {}

  static empty() {
    return new DomainInfo(new Domain('', '', []), [], []);
  }

  public isNew() {
    return this.domain.id == '';
  }

  public canHaveNewChild(): boolean {
    return this.domain.name != 'unallocated';
  }
}
