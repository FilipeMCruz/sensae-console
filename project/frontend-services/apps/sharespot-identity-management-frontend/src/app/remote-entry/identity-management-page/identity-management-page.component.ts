import {Component} from '@angular/core';
import {DynamicDatabase, DynamicDataSource, DynamicFlatNode,} from '../dinamic-data-source/dinamic-data-source';
import {FlatTreeControl} from '@angular/cdk/tree';
import {AuthService} from '@frontend-services/simple-auth-lib';

@Component({
  selector: 'frontend-services-device-record-page',
  templateUrl: './identity-management-page.component.html',
  styleUrls: ['./identity-management-page.component.scss'],
})
export class IdentityManagementPageComponent {
  constructor(private database: DynamicDatabase, private authService: AuthService) {
    this.treeControl = new FlatTreeControl<DynamicFlatNode>(
      this.getLevel,
      this.isExpandable
    );
    this.dataSource = new DynamicDataSource(this.treeControl, database);

    database
      .initialData(authService.getDomains())
      .subscribe((value) => (this.dataSource.data = value));
  }

  treeControl: FlatTreeControl<DynamicFlatNode>;

  dataSource: DynamicDataSource;

  getLevel = (node: DynamicFlatNode) => node.level;

  isExpandable = (node: DynamicFlatNode) => node.expandable;

  hasChild = (_: number, _nodeData: DynamicFlatNode) => _nodeData.expandable;

  onNewDomain(event: DynamicFlatNode) {
    this.database.createNewDomain(event.item)
      .subscribe(_ => this.dataSource.updateParent(event));
  }
}
