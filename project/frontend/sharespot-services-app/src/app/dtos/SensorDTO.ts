export interface SensorDTO {
  locations: SensorDataDTO
}

export interface SensorDataDTO {
  dataId: string;
  deviceId: string;
  reportDate: string;
  data: SensorDataDetailsDTO;
}

export interface SensorDataDetailsDTO {
  latitude: number;
  longitude: number;
}
