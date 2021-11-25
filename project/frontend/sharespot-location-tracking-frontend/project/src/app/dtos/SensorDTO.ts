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
  device: SensorDTO;
  reportedAt: string;
  data: GPSDataDetailsDTO;
  record: Array<RecordEntryDTO>;
}

export interface SensorDTO {
  id: string;
  name: string;
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
