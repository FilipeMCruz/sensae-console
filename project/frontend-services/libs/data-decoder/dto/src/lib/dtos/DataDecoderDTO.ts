export interface DataDecoderQuery {
  decoder: Array<DataDecoderResultDTO>;
}

export interface DataDecoderInput {
  decoder: DataDecoderDTO;
}

export interface DataDecoderResult {
  index: DataDecoderResultDTO;
}

export interface DataDecoderDelete {
  delete: SensorTypeIdDTO;
}

export interface DataDecoderDTO {
  data: SensorTypeIdDTO;
  script: string;
}

export interface DataDecoderResultDTO {
  data: SensorTypeIdDTO;
  script: string;
  lastTimeSeen: number;
}

export interface SensorTypeIdDTO {
  type: string;
}
