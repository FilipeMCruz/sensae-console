export interface DeviceRecordQuery {
  deviceRecords: Array<DeviceRecordDTO>
}

export interface DeviceRecordRegister {
  index: DeviceRecordDTO
}

export interface DeviceRecordDelete {
  delete: DeviceDTO
}

export interface DeviceDTO {
  deviceId: string
}

export interface DeviceRecordDTO {
  deviceId: string;
  entries: Array<RecordEntryDTO>
}

export interface RecordEntryDTO {
  label: string | SensorDataRecordLabelDTO;
  content: string;
  type: RecordTypeDTO;
}

export enum RecordTypeDTO {
  BASIC= "BASIC",
  SENSOR_DATA = "SENSOR_DATA"
}

export enum SensorDataRecordLabelDTO {
  GPS_LATITUDE = "gpsLatitude",
  GPS_LONGITUDE = "gpsLongitude"
}
