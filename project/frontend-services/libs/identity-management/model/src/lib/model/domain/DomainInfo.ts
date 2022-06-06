import {Domain} from './Domain';
import {TenantInfo} from '../tenant/TenantInfo';
import {DeviceInfo} from '../device/DeviceInfo';

export class DomainInfo {
  constructor(
    public domain: Domain,
    public tenants: Array<TenantInfo>,
    public devices: Array<DeviceInfo>
  ) {
  }

  static empty(path: string[]) {
    return new DomainInfo(new Domain('', '', path, []), [], []);
  }

  static of(domain: Domain) {
    return new DomainInfo(domain, [], []);
  }

  public isNew() {
    return this.domain.id == '';
  }

  public canHaveNewChild(): boolean {
    return this.domain.name != 'unallocated' && this.domain.name != 'public';
  }

  public isUnallocated(): boolean {
    return this.domain.name == 'unallocated';
  }

  public isRoot(): boolean {
    return this.domain.path.length == 1;
  }

  public isPublic() {
    return this.domain.name == 'public';
  }
}
