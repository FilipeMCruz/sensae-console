export interface FilteredByContentSensorDTO {
  locationByContent: SensorDataDTO
}

export interface SensorDTO {
  locations: SensorDataDTO
}

export interface FilteredSensorDTO {
  location: SensorDataDTO
}

export interface SensorDataDTO {
  dataId: string;
  device: DeviceDTO;
  reportedAt: string;
  data: GPSDataDetailsDTO;
}

export interface DeviceDTO {
  id: string;
  name: string;
  records: Array<RecordEntryDTO>;
}

export interface SensorDataDetailsDTO {
  latitude: number;
  longitude: number;
}

export interface GPSDataDetailsDTO {
  gps: SensorDataDetailsDTO
}

export interface RecordEntryDTO {
  label: string;
  content: string;
}
