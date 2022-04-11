export interface CreateGardenResultDTO {
  createGarden: GardeningArea
}

export interface UpdateGardenResultDTO {
  updateGarden: GardeningArea
}

export interface QueryHistoryResultDTO {
  history: SensorDataHistory[]
}

export interface QueryGardensResultDTO {
  fetchGardens: GardeningArea[]
}

export interface QueryLatestDataResultDTO {
  fetchLatestData: SensorData[]
}

export interface SubscribeToDataResultDTO {
  data: SensorData
}

export interface SubscribeToDataByContentResultDTO {
  dataByContent: SensorData
}

export interface SubscribeToDataByDevicesResultDTO {
  dataByDevices: SensorData
}

export interface SubscribeToDataByGardensResultDTO {
  dataByGardens: SensorData
}

export interface UpdateGardenParamsDTO {
  instructions: UpdateGardeningAreaCommand
}

export interface CreateGardenParamsDTO {
  instructions: CreateGardeningAreaCommand
}

export interface QueryLatestDataParamsDTO {
  filters: LatestDataQueryFilters
}

export interface QueryHistoryDataParamsDTO {
  filters: HistoryQueryFilters
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

export interface SensorData {
  dataId: string
  device: Device
  reportedAt: string
  data: SensorDataDetails
}

export interface Device {
  id: string
  name: string
  records: RecordEntry[]
}

export interface RecordEntry {
  label: string
  content: string
}

interface SensorDataDetails {
  gps: GPSDataDetails
}

export interface ParkSensorDataDetails extends SensorDataDetails {
  gps: GPSDataDetails
  soilMoisture: SoilMoistureDataDetails
  illuminance: IlluminanceDataDetails
}

export interface StoveSensorDataDetails extends SensorDataDetails {
  gps: GPSDataDetails
  temperature: TemperatureDataDetails
  humidity: HumidityDataDetails
}

export interface ValveDataDetails extends SensorDataDetails {
  gps: GPSDataDetails
  valve: ValveStatusDataDetails
}

export interface TemperatureDataDetails {
  celsius: number
}

export interface ValveStatusDataDetails {
  status: ValveStatusDataDetailsType
}

enum ValveStatusDataDetailsType {
  OPEN,
  CLOSE
}

export interface HumidityDataDetails {
  gramsPerCubicMeter: number
}

export interface SoilMoistureDataDetails {
  percentage: number
}

export interface IlluminanceDataDetails {
  lux: number
}

export interface GPSDataDetails {
  longitude: number
  latitude: number
  altitude: number
}

export interface HistoryQueryFilters {
  devices: string[]
  gardens: string[]
  startTime: string
  endTime: string
}

export interface LatestDataQueryFilters {
  devices: string[]
  gardens: string[]
}

export interface SensorDataHistory {
  id: string
  ledger: [DeviceLedgerHistoryEntry]
}

export interface DeviceLedgerHistoryEntry {
  name: string
  gps: GPSDataDetails
  records: RecordEntry[]
  data: SensorDataHistoryDetails[]
}


interface SensorDataHistoryDetails {
  id: string
  reportedAt: string
}

export interface ParkSensorDataHistoryDetails extends SensorDataHistoryDetails {
  id: string
  reportedAt: string
  soilMoisture: SoilMoistureDataDetails
  illuminance: IlluminanceDataDetails
}

export interface StoveSensorDataHistoryDetails extends SensorDataHistoryDetails {
  id: string
  reportedAt: string
  temperature: TemperatureDataDetails
  humidity: HumidityDataDetails
}

export interface ValveDataHistoryDetails extends SensorDataHistoryDetails {
  id: string
  reportedAt: string
  valve: ValveStatusDataDetails
}

export interface GardeningArea {
  id: string
  name: string
  area: AreaBoundary[]
}

export interface AreaBoundary {
  position: number
  longitude: number
  latitude: number
  altitude: number
}

export interface CreateGardeningAreaCommand {
  name: string
  area: AreaBoundaryCommandDetails[]
}

export interface UpdateGardeningAreaCommand {
  id: string
  name: string
  area: AreaBoundaryCommandDetails[]
}

export interface AreaBoundaryCommandDetails {
  position: number
  longitude: number
  latitude: number
  altitude: number
}
