export interface CreateIrrigationZoneResultDTO {
  createIrrigationZone: IrrigationZoneDTO
}

export interface UpdateIrrigationZoneResultDTO {
  updateIrrigationZone: IrrigationZoneDTO
}

export interface DeleteIrrigationZoneResultDTO {
  deleteIrrigationZone: IrrigationZoneDTO
}

export interface QueryHistoryResultDTO {
  history: SensorDataHistoryDTO[]
}

export interface QueryIrrigationZonesResultDTO {
  fetchIrrigationZones: IrrigationZoneDTO[]
}

export interface QueryLatestDataResultDTO {
  fetchLatestData: SensorDataDTO[]
}

export interface SwitchValveResultDTO {
  switchValve: boolean
}

export interface SubscribeToDataResultDTO {
  data: SensorDataDTO
}

export interface UpdateIrrigationZoneParamsDTO {
  instructions: UpdateIrrigationZoneCommandDTO
}

export interface DeleteIrrigationZoneParamsDTO {
  instructions: DeleteIrrigationZoneCommandDTO
}

export interface CreateIrrigationZoneParamsDTO {
  instructions: CreateIrrigationZoneCommandDTO
}

export interface QueryLatestDataParamsDTO {
  filters: LatestDataQueryFiltersDTO
}

export interface QueryHistoryDataParamsDTO {
  filters: HistoryQueryFiltersDTO
}

export interface SubscribeToDataParamsDTO {
  irrigationZones: string[]
  devices: string[]
  content: string
}

export interface HistoryQueryFiltersDTO {
  devices: string[]
  irrigationZones: string[]
  startTime: string
  endTime: string
}

export interface LatestDataQueryFiltersDTO {
  devices: string[]
  irrigationZones: string[]
}

export interface CreateIrrigationZoneCommandDTO {
  name: string
  area: AreaBoundaryDTO[]
}

export interface UpdateIrrigationZoneCommandDTO {
  id: string
  name: string
  area: AreaBoundaryDTO[]
}

export interface DeleteIrrigationZoneCommandDTO {
  id: string
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
  remoteControl: boolean
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
  OPEN = "OPEN",
  CLOSE = "CLOSE"
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

export interface IrrigationZoneDTO {
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
  PARK_SENSOR = "PARK_SENSOR",
  STOVE_SENSOR = "STOVE_SENSOR",
  VALVE = "VALVE"
}
