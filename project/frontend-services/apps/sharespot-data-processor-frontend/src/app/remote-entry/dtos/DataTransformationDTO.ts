export interface DataTransformationQuery {
  transformation: Array<DataTransformationDTO>
}

export interface DataTransformationInput {
  index: DataTransformationDTO
}

export interface DataTransformationDelete {
  delete: SensorTypeIdDTO
}

export interface DataTransformationDTO {
  data: SensorTypeIdDTO;
  entries: Array<PropertyTransformationDTO>
}

export interface SensorTypeIdDTO {
  type: string
}

export interface PropertyTransformationDTO {
  oldPath: string;
  newPath: PropertyNameDTO;
}

export enum PropertyNameDTO {
  DATA_ID = "DATA_ID",
  DEVICE_ID = "DEVICE_ID",
  DEVICE_NAME = "DEVICE_NAME",
  DEVICE_RECORDS = "DEVICE_RECORDS",
  REPORTED_AT = "REPORTED_AT",
  LATITUDE = "LATITUDE",
  LONGITUDE = "LONGITUDE",
  TEMPERATURE = "TEMPERATURE",
  MOTION = "MOTION",
  VELOCITY = "VELOCITY",
  AQI = "AQI",
  HUMIDITY = "HUMIDITY",
  PRESSURE = "PRESSURE",
  INVALID = "INVALID"
}
