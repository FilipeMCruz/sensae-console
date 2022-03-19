export interface DataDecoderQuery {
  decoder: Array<DataDecoderDTO>;
}

export interface DataDecoderInput {
  decoder: DataDecoderDTO;
}

export interface DataDecoderResult {
  index: DataDecoderDTO;
}

export interface DataDecoderDelete {
  delete: SensorTypeIdDTO;
}

export interface DataDecoderDTO {
  data: SensorTypeIdDTO;
  script: string;
}

export interface SensorTypeIdDTO {
  type: string;
}
