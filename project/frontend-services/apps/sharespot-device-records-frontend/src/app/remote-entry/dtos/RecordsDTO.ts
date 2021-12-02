export interface DeviceRecordQuery {
  deviceRecords: Array<DeviceRecordDTO>
}

export interface DeviceRecordRegister {
  index: DeviceRecordDTO
}

export interface DeviceRecordDelete {
  delete: DeviceIdDTO
}

export interface DeviceIdDTO {
  deviceId: string
}

export interface DeviceRecordDTO {
  device: DeviceDTO;
  entries: Array<RecordEntryDTO>
}

export interface DeviceDTO {
  id: string;
  name: string;
}

export interface RecordEntryDTO {
  label: string | SensorDataRecordLabelDTO;
  content: string;
  type: RecordTypeDTO;
}

export enum RecordTypeDTO {
  BASIC = "BASIC",
  SENSOR_DATA = "SENSOR_DATA"
}

export enum SensorDataRecordLabelDTO {
  GPS_LATITUDE = "gpsLatitude",
  GPS_LONGITUDE = "gpsLongitude"
}
