export interface CreateGardenResultDTO {
  createGarden: GardeningAreaDTO
}

export interface UpdateGardenResultDTO {
  updateGarden: GardeningAreaDTO
}

export interface QueryHistoryResultDTO {
  history: SensorDataHistoryDTO[]
}

export interface QueryGardensResultDTO {
  fetchGardens: GardeningAreaDTO[]
}

export interface QueryLatestDataResultDTO {
  fetchLatestData: SensorDataDTO[]
}

export interface SubscribeToDataResultDTO {
  data: SensorDataDTO
}

export interface SubscribeToDataByContentResultDTO {
  dataByContent: SensorDataDTO
}

export interface SubscribeToDataByDevicesResultDTO {
  dataByDevices: SensorDataDTO
}

export interface SubscribeToDataByGardensResultDTO {
  dataByGardens: SensorDataDTO
}

export interface UpdateGardenParamsDTO {
  instructions: UpdateGardeningAreaCommandDTO
}

export interface CreateGardenParamsDTO {
  instructions: CreateGardeningAreaCommandDTO
}

export interface QueryLatestDataParamsDTO {
  filters: LatestDataQueryFiltersDTO
}

export interface QueryHistoryDataParamsDTO {
  filters: HistoryQueryFiltersDTO
}

export interface SubscribeToDataParamsDTO {
  Authorization: string
}

export interface SubscribeToDataByContentParamsDTO {
  Authorization: string
  content: string
}

export interface SubscribeToDataByDevicesParamsDTO {
  Authorization: string
  devices: string[]
}

export interface SubscribeToDataByGardensParamsDTO {
  Authorization: string
  gardens: string[]
}

export interface SubscribeToDataParamsDTO {
  Authorization: string
}

export interface HistoryQueryFiltersDTO {
  devices: string[]
  gardens: string[]
  startTime: string
  endTime: string
}

export interface LatestDataQueryFiltersDTO {
  devices: string[]
  gardens: string[]
}

export interface CreateGardeningAreaCommandDTO {
  name: string
  area: AreaBoundaryCommandDetailsDTO[]
}

export interface UpdateGardeningAreaCommandDTO {
  id: string
  name: string
  area: AreaBoundaryCommandDetailsDTO[]
}

export interface AreaBoundaryCommandDetailsDTO {
  position: number
  longitude: number
  latitude: number
  altitude: number
}

export interface SensorDataDTO {
  dataId: string
  device: DeviceDTO
  reportedAt: string
  data: SensorDataDetailsDTO
}

export interface DeviceDTO {
  id: string
  type: DeviceTypeDTO
  name: string
  records: RecordEntryDTO[]
}

export interface RecordEntryDTO {
  label: string
  content: string
}

export interface SensorDataDetailsDTO {
  gps: GPSDataDetailsDTO
}

export interface ParkSensorDataDetailsDTO extends SensorDataDetailsDTO {
  gps: GPSDataDetailsDTO
  soilMoisture: SoilMoistureDataDetailsDTO
  illuminance: IlluminanceDataDetailsDTO
}

export interface StoveSensorDataDetailsDTO extends SensorDataDetailsDTO {
  gps: GPSDataDetailsDTO
  temperature: TemperatureDataDetailsDTO
  humidity: HumidityDataDetailsDTO
}

export interface ValveDataDetailsDTO extends SensorDataDetailsDTO {
  gps: GPSDataDetailsDTO
  valve: ValveStatusDataDetailsDTO
}

export interface TemperatureDataDetailsDTO {
  celsius: number
}

export interface ValveStatusDataDetailsDTO {
  status: ValveStatusDataDetailsTypeDTO
}

export enum ValveStatusDataDetailsTypeDTO {
  OPEN,
  CLOSE
}

export interface HumidityDataDetailsDTO {
  gramsPerCubicMeter: number
}

export interface SoilMoistureDataDetailsDTO {
  percentage: number
}

export interface IlluminanceDataDetailsDTO {
  lux: number
}

export interface GPSDataDetailsDTO {
  longitude: number
  latitude: number
  altitude: number
}

export interface SensorDataHistoryDTO {
  id: string
  type: DeviceTypeDTO
  ledger: DeviceLedgerHistoryEntryDTO[]
}

export interface DeviceLedgerHistoryEntryDTO {
  name: string
  gps: GPSDataDetailsDTO
  records: RecordEntryDTO[]
  data: SensorDataHistoryDetailsDTO[]
}

export interface SensorDataHistoryDetailsDTO {
  id: string
  reportedAt: string
}

export interface ParkSensorDataHistoryDetailsDTO extends SensorDataHistoryDetailsDTO {
  id: string
  reportedAt: string
  soilMoisture: SoilMoistureDataDetailsDTO
  illuminance: IlluminanceDataDetailsDTO
}

export interface StoveSensorDataHistoryDetailsDTO extends SensorDataHistoryDetailsDTO {
  id: string
  reportedAt: string
  temperature: TemperatureDataDetailsDTO
  humidity: HumidityDataDetailsDTO
}

export interface ValveDataHistoryDetailsDTO extends SensorDataHistoryDetailsDTO {
  id: string
  reportedAt: string
  valve: ValveStatusDataDetailsDTO
}

export interface GardeningAreaDTO {
  id: string
  name: string
  area: AreaBoundaryDTO[]
}

export interface AreaBoundaryDTO {
  position: number
  longitude: number
  latitude: number
  altitude: number
}

export enum DeviceTypeDTO {
  PARK_SENSOR,
  STOVE_SENSOR,
  VALVE
}
