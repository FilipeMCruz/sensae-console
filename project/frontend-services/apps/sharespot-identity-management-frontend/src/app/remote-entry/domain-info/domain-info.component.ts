import {Component, EventEmitter, Input, OnChanges, OnInit, Output,} from '@angular/core';
import {
  DeviceInfo,
  DomainInfo,
  DomainPermissionType,
  TenantInfo,
} from '@frontend-services/identity-management/model';
import {DynamicFlatNode} from '../dinamic-data-source/dinamic-data-source';
import {
  AddDevice,
  AddTenant,
  ChangeDomain,
  RemoveDevice,
  RemoveTenant,
} from '@frontend-services/identity-management/services';
import {AuthService} from "@frontend-services/simple-auth-lib";

@Component({
  selector: 'frontend-services-domain-info',
  templateUrl: './domain-info.component.html',
  styleUrls: ['./domain-info.component.scss'],
})
export class DomainInfoComponent implements OnChanges, OnInit {
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
  currentPermissions: { perm: DomainPermissionType, checked: boolean }[] = [];

  constructor(
    private removeTenantService: RemoveTenant,
    private addTenantService: AddTenant,
    private removeDeviceService: RemoveDevice,
    private addDeviceService: AddDevice,
    private authService: AuthService,
    private changeDomainService: ChangeDomain
  ) {
  }

  ngOnInit(): void {
    this.currentPermissions = this.computePermissions();
  }

  canChangeDomains() {
    return this.authService.isAllowed(Array.of("identity_management:domains:edit"))
  }

  canCreateDomains() {
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

  computePermissions() {
    const hasParent = this.entry.item.domain.path.length > 1;
    if (hasParent) {
      const parentId = this.entry.item.domain.path[this.entry.item.domain.path.length - 2];
      const domainInfo = this.domains.find(d => d.domain.id === parentId);
      if (domainInfo) {
        return domainInfo.domain.permissions.map(p => {
          if (this.entry.item.domain.permissions.includes(p)) {
            return {perm: p, checked: true}
          } else {
            return {perm: p, checked: false}
          }
        });
      }
    }
    return this.entry.item.domain.permissions.map(p => {
      return {perm: p, checked: true}
    }).sort((n1, n2) => n1.perm.localeCompare(n2.perm));
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

  addDevice(device: DeviceInfo, domain: DomainInfo) {
    this.addDeviceService
      .mutate(device.id, domain.domain.id)
      .subscribe((next) =>
        this.emmitNewDeviceInDomain.emit({domain, device: next})
      );
  }

  saveDomainChanges() {
    const permissionTypes = this.currentPermissions.filter(p => p.checked).map(p => p.perm);
    this.changeDomainService
      .mutate(this.entry.item.domain.id, this.entry.item.domain.name, permissionTypes)
      .subscribe(next => {
        this.entry.item.domain.permissions = next.permissions;
        this.currentPermissions.forEach(p => {
          const found = next.permissions.find(perm => perm === p.perm);
          p.checked = !!found;
        })
      })
  }
}
