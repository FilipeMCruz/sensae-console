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
  history: Array<GPSSensorDataHistory>;
}

export interface SensorDataDTO {
  dataId: string;
  device: DeviceDTO;
  reportedAt: string;
  data: SensorDataDetailsDTO;
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

export interface SensorDataDetailsDTO {
  gps: GPSDataDetailsDTO;
  status: StatusDataDetailsDTO;
}

export interface RecordEntryDTO {
  label: string;
  content: string;
}

export interface GPSSensorDataQuery {
  device: Array<string>;
  startTime: string;
  endTime: string;
}

export interface GPSSensorDataHistory {
  deviceId: string;
  deviceName: string;
  startTime: string;
  endTime: string;
  distance: number;
  segments: Array<GPSSegmentDetailsDTO>;
}

export interface GPSSegmentDetailsDTO {
  type: GPSSegmentType;
  steps: Array<GPSStepDetailsDTO>;
}

export interface GPSStepDetailsDTO {
  gps: GPSDataDetailsDTO;
  status: StatusDataDetailsDTO;
  reportedAt: string;
}

export interface GPSSensorLatestData {
  latest: Array<SensorDataDTO>;
}

export interface FilteredByDeviceGPSSensorLatestData {
  latestByDevice: Array<SensorDataDTO>;
}

export enum GPSSegmentType {
  INACTIVE = 'INACTIVE',
  ACTIVE = 'ACTIVE',
  UNKNOWN_ACTIVE = 'UNKNOWN_ACTIVE',
  UNKNOWN_INACTIVE = 'UNKNOWN_INACTIVE',
}
