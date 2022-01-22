export interface FilteredByContentSensorDTO {
  locationByContent: SensorDataDTO;
}

export interface SensorDTO {
  locations: SensorDataDTO;
}

export interface FilteredSensorDTO {
  location: SensorDataDTO;
}

export interface HistorySensorDTO {
  history: GPSSensorDataHistory;
}

export interface SensorDataDTO {
  dataId: string;
  device: DeviceDTO;
  reportedAt: string;
  data: SensorSataDetailsDTO;
}

export interface DeviceDTO {
  id: string;
  name: string;
  records: Array<RecordEntryDTO>;
}

export interface GPSDataDetailsDTO {
  latitude: number;
  longitude: number;
}

export interface StatusDataDetailsDTO {
  motion: string;
}

export interface SensorSataDetailsDTO {
  gps: GPSDataDetailsDTO;
  status: StatusDataDetailsDTO;
}

export interface RecordEntryDTO {
  label: string;
  content: string;
}

export interface GPSSensorDataQuery {
  device: string;
  startTime: string;
  endTime: string;
}

export interface GPSSensorDataHistory {
  deviceId: string;
  deviceName: string;
  startTime: string;
  endTime: string;
  distance: number;
  data: Array<GPSDataDetailsDTO>;
}

export interface GPSSensorLatestData {
  latest: Array<SensorDTO>;
}
