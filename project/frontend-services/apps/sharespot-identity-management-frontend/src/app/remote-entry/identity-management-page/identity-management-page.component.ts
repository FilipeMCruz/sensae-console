import { Component } from '@angular/core';
import {
  DynamicDatabase,
  DynamicDataSource,
  DynamicFlatNode,
} from '../dinamic-data-source/dinamic-data-source';
import { FlatTreeControl } from '@angular/cdk/tree';
import {
  DeviceInfo,
  DomainInfo,
  TenantInfo,
} from '@frontend-services/identity-management/model';

@Component({
  selector: 'frontend-services-device-record-page',
  templateUrl: './identity-management-page.component.html',
  styleUrls: ['./identity-management-page.component.scss'],
})
export class IdentityManagementPageComponent {
  constructor(private database: DynamicDatabase) {
    this.treeControl = new FlatTreeControl<DynamicFlatNode>(
      this.getLevel,
      this.isExpandable
    );
    this.dataSource = new DynamicDataSource(this.treeControl, database);

    database.initialData().subscribe((value) => {
      const startLength = Math.min(
        ...value.map((v) => v.item.domain.path.length)
      );
      this.dataSource.data = value.filter(
        (v) => v.item.domain.path.length == startLength
      );
    });
  }

  treeControl: FlatTreeControl<DynamicFlatNode>;

  dataSource: DynamicDataSource;

  getLevel = (node: DynamicFlatNode) => node.level;

  isExpandable = (node: DynamicFlatNode) => node.expandable;

  hasChild = (_: number, _nodeData: DynamicFlatNode) => _nodeData.expandable;

  onNewDomain(event: DynamicFlatNode) {
    this.database.createNewDomain(event.item).subscribe((domain) => {
      this.dataSource.updateNode(
        new DynamicFlatNode(DomainInfo.of(domain), event.level, true)
      );
    });
  }

  getDomains() {
    return this.dataSource.data
      .map((d) => d.item)
      .filter((d) => d.domain.id != '');
  }

  onNewTenantInDomain(event: { domain: DomainInfo; tenant: TenantInfo }) {
    this.dataSource.updateDomainWithTenant(event.domain, event.tenant);
  }

  onNewDeviceInDomain(event: { domain: DomainInfo; device: DeviceInfo }) {
    this.dataSource.updateDomainWithDevice(event.domain, event.device);
  }
}
