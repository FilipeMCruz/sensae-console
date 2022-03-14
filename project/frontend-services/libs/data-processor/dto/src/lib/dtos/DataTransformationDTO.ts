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
  newPath: PropertyNameDTO;
}

export enum PropertyNameDTO {
  DATA_ID = 'DATA_ID',
  DEVICE_ID = 'DEVICE_ID',
  DEVICE_NAME = 'DEVICE_NAME',
  REPORTED_AT = 'REPORTED_AT',
  LATITUDE = 'LATITUDE',
  LONGITUDE = 'LONGITUDE',
  TEMPERATURE = 'TEMPERATURE',
  MOTION = 'MOTION',
  VELOCITY = 'VELOCITY',
  AQI = 'AQI',
  HUMIDITY = 'HUMIDITY',
  PRESSURE = 'PRESSURE',
  INVALID = 'INVALID',
}
