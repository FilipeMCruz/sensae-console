export enum DomainPermissionType {
  READ_DEVICE_RECORD = 'Device Record | See Devices Info',
  DELETE_DEVICE_RECORD = "Device Record | Delete Devices Info",
  EDIT_DEVICE_RECORD = "Device Record | Edit Devices Info",

  READ_DATA_TRANSFORMATION = 'Data Transformation | See Mappers',
  DELETE_DATA_TRANSFORMATION = 'Data Transformation | Delete Mappers',
  EDIT_DATA_TRANSFORMATION = 'Data Transformation | Edit Mappers',

  READ_DATA_DECODER = 'Data Decoder | See Scripts',
  EDIT_DATA_DECODER = 'Data Decoder | Edit Scripts',
  DELETE_DATA_DECODER = 'Data Decoder | Delete Scripts',

  READ_LIVE_DATA_FLEET_MANAGEMENT = 'Fleet Management | See Live Data',
  READ_PAST_DATA_FLEET_MANAGEMENT = 'Fleet Management | See Past Data',

  EDIT_DOMAIN = 'Identity Management | Edit Domain Permissions',
  CREATE_DOMAIN = 'Identity Management | Create Domain',
  READ_DOMAIN = 'Identity Management | See Domains Info',

  EDIT_DEVICE = 'Identity Management | Move Devices',
  READ_DEVICE = 'Identity Management | See Devices',

  EDIT_TENANT = 'Identity Management | Move Tenants',
  READ_TENANT = 'Identity Management | See Tenants',

  CREATE_GARDEN_SMART_IRRIGATION = 'Smart Irrigation | Create Gardens',
  EDIT_GARDEN_SMART_IRRIGATION = 'Smart Irrigation | Edit Gardens',
  DELETE_GARDEN_SMART_IRRIGATION = 'Smart Irrigation | Delete Gardens',
  READ_GARDEN_SMART_IRRIGATION = 'Smart Irrigation | See Gardens',

  READ_LIVE_DATA_SMART_IRRIGATION = 'Smart Irrigation | See Live Data',
  READ_PAST_DATA_SMART_IRRIGATION = 'Smart Irrigation | See Past Data',

  CONTROL_VALVE_SMART_IRRIGATION = 'Smart Irrigation | Control Valves',
  ERROR = 'Error',
}
