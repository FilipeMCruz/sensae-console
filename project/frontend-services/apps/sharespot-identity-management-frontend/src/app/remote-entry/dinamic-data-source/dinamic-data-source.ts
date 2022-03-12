import {CollectionViewer, DataSource, SelectionChange,} from '@angular/cdk/collections';
import {BehaviorSubject, forkJoin, merge, Observable} from 'rxjs';
import {FlatTreeControl} from '@angular/cdk/tree';
import {map} from 'rxjs/operators';
import {Injectable} from '@angular/core';
import {CreateDomain, GetChildDomainsInfo, GetDomainInfo,} from '@frontend-services/identity-management/services';
import {Domain, DomainInfo, TenantInfo} from '@frontend-services/identity-management/model';
import {AuthService} from "@frontend-services/simple-auth-lib";

@Injectable({providedIn: 'root'})
export class DynamicDatabase {
  constructor(
    private getChildDomainsInfo: GetChildDomainsInfo,
    private getDomainInfo: GetDomainInfo,
    private createDomain: CreateDomain,
    private authService: AuthService
  ) {
  }

  initialData(): Observable<DynamicFlatNode[]> {
    const domainObs: Observable<DynamicFlatNode>[] = this.authService.getDomains().map((d) =>
      this.getDomainInfo
        .query(d)
        .pipe(map((next) => new DynamicFlatNode(next, 0, true)))
    );
    return forkJoin(domainObs);
  }

  userCanCreateDomains() {
    return this.authService.isAllowed(['identity_management:domains:create']);
  }

  getChildren(node: string): Observable<DomainInfo[]> {
    return this.getChildDomainsInfo.query(node);
  }

  createNewDomain(event: DomainInfo): Observable<Domain> {
    return this.createDomain.mutate(event.domain.path[event.domain.path.length - 2], event.domain.name);
  }
}

export class DynamicFlatNode {
  constructor(
    public item: DomainInfo,
    public level = 1,
    public expandable = true,
    public isLoading = false
  ) {
  }
}

export class DynamicDataSource implements DataSource<DynamicFlatNode> {
  dataChange = new BehaviorSubject<DynamicFlatNode[]>([]);

  get data(): DynamicFlatNode[] {
    return this.dataChange.value;
  }

  set data(value: DynamicFlatNode[]) {
    this._treeControl.dataNodes = value;
    this.dataChange.next(value);
  }

  constructor(
    private _treeControl: FlatTreeControl<DynamicFlatNode>,
    private _database: DynamicDatabase
  ) {
  }

  connect(collectionViewer: CollectionViewer): Observable<DynamicFlatNode[]> {
    this._treeControl.expansionModel.changed.subscribe((change) => {
      if (
        (change as SelectionChange<DynamicFlatNode>).added ||
        (change as SelectionChange<DynamicFlatNode>).removed
      ) {
        this.handleTreeControl(change as SelectionChange<DynamicFlatNode>);
      }
    });

    return merge(collectionViewer.viewChange, this.dataChange).pipe(
      map(() => this.data)
    );
  }

  disconnect(collectionViewer: CollectionViewer): void {
  }

  /** Handle expand/collapse behaviors */
  handleTreeControl(change: SelectionChange<DynamicFlatNode>) {
    if (change.added) {
      change.added.forEach((node) => this.toggleNode(node, true));
    }
    if (change.removed) {
      change.removed
        .slice()
        .reverse()
        .forEach((node) => this.toggleNode(node, false));
    }
  }

  /**
   * Toggle the node, remove from display list
   */
  toggleNode(node: DynamicFlatNode, expand: boolean) {
    const children = this._database.getChildren(node.item.domain.id);
    const index = this.data.indexOf(node);
    if (!children || index < 0) {
      // If no children, or cannot find the node, no op
      return;
    }

    if (expand) {
      node.isLoading = true;
      children.subscribe((domains) => {
        const nodes = domains.map(
          (info: DomainInfo) =>
            new DynamicFlatNode(info, node.level + 1, info.canHaveNewChild())
        );
        if (node.item.canHaveNewChild() && this._database.userCanCreateDomains()) {
          nodes.push(
            new DynamicFlatNode(DomainInfo.empty([...node.item.domain.path, '']), node.level + 1, false)
          );
        }
        this.data.splice(index + 1, 0, ...nodes);
        this.dataChange.next(this.data);
        node.isLoading = false;
      });
    } else {
      let count = 0;
      for (
        let i = index + 1;
        i < this.data.length && this.data[i].level > node.level;
        i++, count++
      ) {
      }
      this.data.splice(index + 1, count);
      this.dataChange.next(this.data);
    }
  }

  updateNode(node: DynamicFlatNode) {
    const parent = this.dataChange.value.find(d => d.item.domain.id === node.item.domain.path[node.item.domain.path.length - 2]);
    if (!parent) {
      return;
    }
    const index = this.data.indexOf(parent);
    if (index < 0) {
      // If no children, or cannot find the node, no op
      return;
    }
    this.data.splice(index + 1, 0, node);
    this.dataChange.next(this.data);
  }

  updateDomainWithTenant(domain: DomainInfo, tenant: TenantInfo) {
    const found = this.dataChange.value.find(d => d.item.domain.id === domain.domain.id);
    if (!found) {
      return;
    }
    found.item.tenants.push(tenant);
  }
}
