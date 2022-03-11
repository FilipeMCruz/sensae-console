import {Component, EventEmitter, Input, Output} from "@angular/core";
import {DeviceInfo, DevicePermissionType, DomainInfo} from "@frontend-services/identity-management/model";
import {DynamicFlatNode} from "../dinamic-data-source/dinamic-data-source";

@Component({
  selector: 'frontend-services-domain-info',
  templateUrl: './domain-info.component.html',
  styleUrls: ['./domain-info.component.scss'],
})
export class DomainInfoComponent {
  @Input() entry: DynamicFlatNode = new DynamicFlatNode(DomainInfo.empty([''])); //This will never happen

  @Output() newDomain = new EventEmitter<DynamicFlatNode>();

  getPermission(device: DeviceInfo): DevicePermissionType {
    const deviceDomainPermission = device.domains.find(d => d.domainId == this.entry.item.domain.id);
    if (deviceDomainPermission) {
      return deviceDomainPermission.permission;
    } else {
      return DevicePermissionType.READ;
    }
  }

  saveNewDomain() {
    this.newDomain.emit(this.entry);
    this.resetView();
  }

  private resetView() {
    const path = [...this.entry.item.domain.path];
    this.entry.item = DomainInfo.empty(path);
  }
}
