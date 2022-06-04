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
  GPS_LONGITUDE = "GPS_LONGITUDE"
}

export interface SubDevice {
  id: string
  ref: number
}
