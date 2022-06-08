export interface ViewDomainResultDTO {
  viewDomain: DomainDTO[];
}

export interface ViewDomainInfoResultDTO {
  viewDomainInfo: DomainInfoDTO;
}

export interface ViewChildDomainInfoResultDTO {
  viewChildDomainsInfo: DomainInfoDTO[];
}

export interface ViewTenantsInDomainResultDTO {
  viewTenantsInDomain: TenantDTO[];
}

export interface ViewDevicesInDomainResultDTO {
  viewDevicesInDomain: DeviceDTO[];
}

export interface ViewDomainQueryDTO {
  domain: ViewDomainDTO;
}

export interface ViewDomainDTO {
  oid: string;
}

export interface CreateDomainQueryDTO {
  domain: CreateDomainDTO;
}

export interface CreateDomainResultDTO {
  createDomain: DomainDTO;
}

export interface ChangeDomainResultDTO {
  changeDomain: DomainDTO;
}

export interface CreateDomainDTO {
  parentDomainOid: string;
  newDomainName: string;
}

export interface AddDeviceResultDTO {
  addDevice: DeviceDTO;
}

export interface AddDeviceQueryDTO {
  instructions: AddDeviceToDomainDTO;
}

export interface AddDeviceToDomainDTO {
  deviceOid: string;
  domainOid: string;
}

export interface RemoveTenantResultDTO {
  removeTenant: TenantDTO;
}

export interface RemoveDeviceResultDTO {
  removeDevice: DeviceDTO;
}

export interface RemoveDeviceQueryDTO {
  instructions: RemoveDeviceFromDomainDTO;
}

export interface RemoveDeviceFromDomainDTO {
  deviceOid: string;
  domainOid: string;
}

export interface AddTenantResultDTO {
  addTenant: TenantDTO;
}

export interface AddTenantQueryDTO {
  instructions: AddTenantToDomainDTO;
}

export interface AddTenantToDomainDTO {
  tenantOid: string;
  domainOid: string;
}

export interface RemoveTenantResultDTO {
  removeTenant: TenantDTO;
}

export interface RemoveTenantQueryDTO {
  instructions: RemoveTenantFromDomainDTO;
}

export interface RemoveTenantFromDomainDTO {
  tenantOid: string;
  domainOid: string;
}

export interface DomainDTO {
  oid: string;
  name: string;
  path: string[];
  permissions: string[];
}

export interface DomainInfoDTO {
  domain: DomainDTO;
  devices: DeviceDTO[];
  tenants: TenantDTO[];
}

export interface TenantDTO {
  oid: string;
  email: string;
  name: string;
}

export interface DeviceDTO {
  oid: string;
  name: string;
  domains: DeviceDomainPermissionDTO[];
}

export interface DeviceDomainPermissionDTO {
  oid: string;
}
