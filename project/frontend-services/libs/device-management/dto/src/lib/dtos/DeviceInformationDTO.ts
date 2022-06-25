export interface DeviceInformationQuery {
  deviceInformation: Array<DeviceInformationDTO>
}

export interface DeviceRecordsInput {
  index: DeviceInformationDTO
}

export interface DeviceRecordDelete {
  delete: DeviceDTO
}

export interface DeviceInformationDTO {
  device: DeviceDTO
  records: Array<RecordEntryDTO>
  staticData: Array<StaticDataEntryDTO>
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
  label: string
  content: string
}

export interface StaticDataEntryDTO {
  label: SensorDataRecordLabelDTO
  content: string
}

export enum SensorDataRecordLabelDTO {
  GPS_LATITUDE = "GPS_LATITUDE",
  GPS_LONGITUDE = "GPS_LONGITUDE",
  GPS_ALTITUDE = "GPS_ALTITUDE",
  BATTERY_MIN_VOLTS = "BATTERY_MIN_VOLTS",
  BATTERY_MAX_VOLTS = "BATTERY_MAX_VOLTS",
  MIN_DISTANCE = "MIN_DISTANCE",
  MAX_DISTANCE = "MAX_DISTANCE",
  ERROR = "ERROR"
}

export interface SubDevice {
  id: string
  ref: number
}

export interface CommandDevice {
  deviceCommand: CommandDTO
}
export interface CommandDTO {
  deviceId: string
  commandId: string
}
