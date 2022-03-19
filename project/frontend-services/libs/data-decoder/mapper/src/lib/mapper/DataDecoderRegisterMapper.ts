import {DataDecoder} from "@frontend-services/data-decoder/model";
import {DataDecoderMapper} from "./DataDecoderMapper";
import {DataDecoderInput, DataDecoderResult} from "@frontend-services/data-decoder/dto";

export class DataDecoderRegisterMapper {
  static modelToDto(model: DataDecoder): DataDecoderInput {
    return {decoder: DataDecoderMapper.modelToDto(model)};
  }

  static dtoToModel(dto: DataDecoderResult): DataDecoder {
    return DataDecoderMapper.dtoToModel(dto.index);
  }
}
