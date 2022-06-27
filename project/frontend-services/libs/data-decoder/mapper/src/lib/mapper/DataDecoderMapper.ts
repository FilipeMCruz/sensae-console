import {SensorTypeMapper} from './SensorTypeMapper';
import {DataDecoder, ScriptContent} from "@frontend-services/data-decoder/model";
import {DataDecoderDTO, DataDecoderResultDTO} from "@frontend-services/data-decoder/dto";

export class DataDecoderMapper {
  static dtoToModel(dto: DataDecoderResultDTO): DataDecoder {
    const sensorTypeId = SensorTypeMapper.dtoToModel(dto.data);
    const script = new ScriptContent(dto.script);
    const d = new Date(0); // The 0 there is the key, which sets the date to the epoch
    d.setUTCMilliseconds(dto.lastTimeSeen);
    return new DataDecoder(sensorTypeId, script, d);
  }

  static modelToDto(model: DataDecoder): DataDecoderDTO {
    return {data: {type: model.data.type}, script: model.script.content};
  }
}
