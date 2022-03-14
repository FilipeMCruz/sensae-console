import { DomainPermissionType } from './DomainPermissionType';

export class Domain {
  constructor(
    public id: string,
    public name: string,
    public path: Array<string>,
    public permissions: Array<DomainPermissionType>
  ) {}
}
