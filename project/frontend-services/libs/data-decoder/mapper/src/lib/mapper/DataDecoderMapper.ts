import {SensorTypeMapper} from './SensorTypeMapper';
import {DataDecoder, ScriptContent} from "@frontend-services/data-decoder/model";
import {DataDecoderDTO} from "@frontend-services/data-decoder/dto";

export class DataDecoderMapper {
  static dtoToModel(dto: DataDecoderDTO): DataDecoder {
    const sensorTypeId = SensorTypeMapper.dtoToModel(dto.data);
    const script = new ScriptContent(dto.script);
    return new DataDecoder(sensorTypeId, script);
  }

  static modelToDto(model: DataDecoder): DataDecoderDTO {
    return {data: {type: model.data.type}, script: model.script.content};
  }
}
