export enum DomainPermissionType {
  READ_DEVICE_RECORDS = 'Access Device Records',
  WRITE_DEVICE_RECORDS = 'Change Device Records',

  READ_DATA_TRANSFORMATIONS = 'Access Data Transformations',
  WRITE_DATA_TRANSFORMATIONS = 'Change Data Transformations',

  READ_DATA_DECODERS = 'Access Data Decoders',
  WRITE_DATA_DECODERS = 'Change Data Decoders',

  READ_FLEET_MANAGEMENT = 'Access Fleet Management',

  WRITE_DOMAINS = 'Change Identity Management Domains',
  READ_DOMAINS = 'Access Identity Management Domains',

  WRITE_DEVICE = 'Change Identity Management Devices',
  READ_DEVICE = 'Access Identity Management Devices',

  WRITE_TENANT = 'Change Identity Management Tenants',
  READ_TENANT = 'Access Identity Management Tenants',
  ERROR = 'Error',
}
