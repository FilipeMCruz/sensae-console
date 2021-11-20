export interface SensorDTO {
  locations: SensorDataDTO
}

export interface FilteredSensorDTO {
  location: SensorDataDTO
}

export interface SensorDataDTO {
  dataId: string;
  deviceId: string;
  reportedAt: string;
  data: SensorDataDetailsDTO;
}

export interface SensorDataDetailsDTO {
  latitude: number;
  longitude: number;
}
