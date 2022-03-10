import {
  AddDeviceQueryDTO,
  AddTenantQueryDTO,
  CreateDomainQueryDTO,
  RemoveDeviceQueryDTO,
  RemoveTenantQueryDTO,
  ViewDomainQueryDTO,
} from '@frontend-services/identity-management/dto';

export class QueryMapper {
  static toViewDomain(domainId: string): ViewDomainQueryDTO {
    return { domain: { oid: domainId } };
  }

  static toCreateDomain(
    parentId: string,
    domainName: string
  ): CreateDomainQueryDTO {
    return { domain: { parentDomainOid: parentId, newDomainName: domainName } };
  }

  static toAddTenant(tenantId: string, domainId: string): AddTenantQueryDTO {
    return { instructions: { tenantOid: tenantId, domainOid: domainId } };
  }

  static toRemoveTenant(
    tenantId: string,
    domainId: string
  ): RemoveTenantQueryDTO {
    return { instructions: { tenantOid: tenantId, domainOid: domainId } };
  }

  static toAddDevice(
    deviceId: string,
    domainId: string,
    write: boolean
  ): AddDeviceQueryDTO {
    return {
      instructions: {
        deviceOid: deviceId,
        domainOid: domainId,
        writePermission: write,
      },
    };
  }

  static toRemoveDevice(
    deviceId: string,
    domainId: string
  ): RemoveDeviceQueryDTO {
    return { instructions: { deviceOid: deviceId, domainOid: domainId } };
  }
}
