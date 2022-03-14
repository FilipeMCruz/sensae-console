import {Component, EventEmitter, Input, OnChanges, Output,} from '@angular/core';
import {DeviceInfo, DevicePermissionType, DomainInfo, TenantInfo,} from '@frontend-services/identity-management/model';
import {DynamicFlatNode} from '../dinamic-data-source/dinamic-data-source';
import {AddDevice, AddTenant, RemoveDevice, RemoveTenant,} from '@frontend-services/identity-management/services';
import {AuthService} from "@frontend-services/simple-auth-lib";

@Component({
  selector: 'frontend-services-domain-info',
  templateUrl: './domain-info.component.html',
  styleUrls: ['./domain-info.component.scss'],
})
export class DomainInfoComponent implements OnChanges {
  @Input() domains: DomainInfo[] = []; //This will never happen

  @Input() entry: DynamicFlatNode = new DynamicFlatNode(DomainInfo.empty([''])); //This will never happen

  @Output() emmitNewDomain = new EventEmitter<DynamicFlatNode>();

  @Output() emmitNewTenantInDomain = new EventEmitter<{
    domain: DomainInfo;
    tenant: TenantInfo;
  }>();

  @Output() emmitNewDeviceInDomain = new EventEmitter<{
    domain: DomainInfo;
    device: DeviceInfo;
  }>();

  currentDomainsForTenants: DomainInfo[] = [];
  currentDomainsForDevices: DomainInfo[] = [];
  panelOpenState = false;

  constructor(
    private removeTenantService: RemoveTenant,
    private addTenantService: AddTenant,
    private removeDeviceService: RemoveDevice,
    private addDeviceService: AddDevice,
    private authService: AuthService
  ) {
  }

  canChangeDomains() {
    return this.authService.isAllowed(Array.of("identity_management:domains:create"))
  }

  canChangeDevices() {
    return this.authService.isAllowed(Array.of("identity_management:device:write"))
  }

  canChangeTenants() {
    return this.authService.isAllowed(Array.of("identity_management:tenant:write"))
  }

  ngOnChanges(): void {
    this.currentDomainsForTenants = this.domains.filter(
      (d) => d.domain.id != this.entry.item.domain.id
    );
    this.currentDomainsForDevices = this.domains
      .filter((d) => d.domain.id != this.entry.item.domain.id)
      .filter((d) => !d.isUnallocated());
  }

  getValidDomainsForDevice(device: DeviceInfo) {
    const deviceDomains = device.domains.map((d) => d.domainId);
    return this.currentDomainsForDevices.filter(
      (d) => !deviceDomains.includes(d.domain.id)
    );
  }

  getValidDomainsForTenant(tenant: TenantInfo) {
    return this.currentDomainsForTenants.filter(
      (d) => !d.tenants.map(t => t.id).includes(tenant.id)
    );
  }

  getPermission(device: DeviceInfo): DevicePermissionType {
    const deviceDomainPermission = device.domains.find(
      (d) => d.domainId == this.entry.item.domain.id
    );
    if (deviceDomainPermission) {
      return deviceDomainPermission.permission;
    } else {
      return DevicePermissionType.READ;
    }
  }

  newDomain() {
    this.emmitNewDomain.emit(this.entry);
    this.resetView();
  }

  private resetView() {
    const path = [...this.entry.item.domain.path];
    this.entry.item = DomainInfo.empty(path);
  }

  removeTenant(tenant: TenantInfo) {
    this.removeTenantService
      .mutate(tenant.id, this.entry.item.domain.id)
      .subscribe((next) => {
        const index = this.entry.item.tenants.findIndex(
          (d) => d.id === next.id
        ); //find index in your array
        this.entry.item.tenants.splice(index, 1);
      });
  }

  addTenant(tenant: TenantInfo, domain: DomainInfo) {
    this.addTenantService
      .mutate(tenant.id, domain.domain.id)
      .subscribe((next) =>
        this.emmitNewTenantInDomain.emit({domain, tenant: next})
      );
  }

  removeDevice(device: DeviceInfo) {
    this.removeDeviceService
      .mutate(device.id, this.entry.item.domain.id)
      .subscribe((next) => {
        const index = this.entry.item.devices.findIndex(
          (d) => d.id === next.id
        ); //find index in your array
        this.entry.item.devices.splice(index, 1);
      });
  }

  addDevice(device: DeviceInfo, domain: DomainInfo, b: boolean) {
    this.addDeviceService
      .mutate(device.id, domain.domain.id, b)
      .subscribe((next) =>
        this.emmitNewDeviceInDomain.emit({domain, device: next})
      );
  }
}
