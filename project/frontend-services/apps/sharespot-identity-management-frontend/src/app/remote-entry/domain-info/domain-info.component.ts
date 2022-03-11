import {Component, Input} from "@angular/core";
import {DeviceInfo, DevicePermissionType, DomainInfo} from "@frontend-services/identity-management/model";

@Component({
  selector: 'frontend-services-domain-info',
  templateUrl: './domain-info.component.html',
  styleUrls: ['./domain-info.component.scss'],
})
export class DomainInfoComponent {
  @Input() entry: DomainInfo = DomainInfo.empty();

  getPermission(device: DeviceInfo): DevicePermissionType {
    const deviceDomainPermission = device.domains.find(d => d.domainId == this.entry.domain.id);
    if (deviceDomainPermission) {
      return deviceDomainPermission.permission;
    } else {
      return DevicePermissionType.READ;
    }
  }
}
