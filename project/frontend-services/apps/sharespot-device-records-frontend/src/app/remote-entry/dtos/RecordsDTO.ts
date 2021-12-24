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
