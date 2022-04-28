export interface DataTransformationQuery {
  transformation: Array<DataTransformationDTO>;
}

export interface DataTransformationInput {
  transformation: DataTransformationDTO;
}

export interface DataTransformationResult {
  index: DataTransformationDTO;
}

export interface DataTransformationDelete {
  delete: SensorTypeIdDTO;
}

export interface DataTransformationDTO {
  data: SensorTypeIdDTO;
  entries: Array<PropertyTransformationDTO>;
}

export interface SensorTypeIdDTO {
  type: string;
}

export interface PropertyTransformationDTO {
  oldPath: string;
  sensorID: number;
  newPath: PropertyNameDTO;

}

export enum PropertyNameDTO {
  AIR_HUMIDITY_GRAMS_PER_CUBIC_METER = 'AIR_HUMIDITY_GRAMS_PER_CUBIC_METER',
  AIR_HUMIDITY_RELATIVE_PERCENTAGE = 'AIR_HUMIDITY_RELATIVE_PERCENTAGE',
  AIR_PRESSURE = "AIR_PRESSURE",
  ALTITUDE = "ALTITUDE",
  AQI = "AQI",
  BATTERY_MAX_VOLTS = "BATTERY_MAX_VOLTS",
  BATTERY_MIN_VOLTS = "BATTERY_MIN_VOLTS",
  BATTERY_PERCENTAGE = "BATTERY_PERCENTAGE",
  BATTERY_VOLTS = "BATTERY_VOLTS",
  CO = "CO",
  CO2 = "CO2",
  DATA_ID = "DATA_ID",
  DEVICE_ID = "DEVICE_ID",
  DEVICE_NAME = "DEVICE_NAME",
  DISTANCE = "DISTANCE",
  ILLUMINANCE = "ILLUMINANCE",
  LATITUDE = "LATITUDE",
  LONGITUDE = "LONGITUDE",
  MAX_DISTANCE = "MAX_DISTANCE",
  MIN_DISTANCE = "MIN_DISTANCE",
  MOTION = "MOTION",
  NH3 = "NH3",
  NO2 = "NO2",
  O3 = "O3",
  OCCUPATION = "OCCUPATION",
  PH = "PH",
  PM10 = "PM10",
  PM2_5 = "PM2_5",
  REPORTED_AT = "REPORTED_AT",
  SOIL_CONDUCTIVITY = "SOIL_CONDUCTIVITY",
  SOIL_MOISTURE = "SOIL_MOISTURE",
  TEMPERATURE = "TEMPERATURE",
  TRIGGER = "TRIGGER",
  VELOCITY = "VELOCITY",
  VOC = "VOC",
  WATER_PRESSURE = "WATER_PRESSURE",
  DEVICE_DOWNLINK = "DEVICE_DOWNLINK",
  INVALID = "INVALID"
}
