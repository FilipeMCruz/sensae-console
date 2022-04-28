export interface DeviceRecordQuery {
  deviceRecords: Array<DeviceRecordDTO>
}

export interface DeviceRecordsInput {
  index: DeviceRecordDTO
}

export interface DeviceRecordDelete {
  delete: DeviceDTO
}

export interface DeviceRecordDTO {
  device: DeviceDTO
  entries: Array<RecordEntryDTO>
  subDevices: Array<SubDevice>
  commands: Array<DeviceCommandDTO>
}

export interface DeviceDTO {
  id: string
  name: string
  downlink: string
}

export interface DeviceCommandDTO {
  id: string
  name: string
  ref: number
  port: number
  payload: string
}

export interface RecordEntryDTO {
  label: string | SensorDataRecordLabelDTO
  content: string
  type: RecordTypeDTO
}

export enum RecordTypeDTO {
  BASIC = 'BASIC',
  SENSOR_DATA = 'SENSOR_DATA',
}

export enum SensorDataRecordLabelDTO {
  GPS_LATITUDE = 'gpsLatitude',
  GPS_LONGITUDE = 'gpsLongitude',
}

export interface SubDevice {
  id: string
  ref: number
}
