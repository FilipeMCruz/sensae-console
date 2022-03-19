import {DataDecoderQuery} from "@frontend-services/data-decoder/dto";
import {DataDecoder} from "@frontend-services/data-decoder/model";
import {DataDecoderMapper} from "./DataDecoderMapper";

export class DataDecoderQueryMapper {
  static dtoToModel(dto: DataDecoderQuery): Array<DataDecoder> {
    return dto.decoder.map((e) =>
      DataDecoderMapper.dtoToModel(e)
    );
  }
}
