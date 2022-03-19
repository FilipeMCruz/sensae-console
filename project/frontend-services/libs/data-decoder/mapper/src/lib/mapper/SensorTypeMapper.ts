import {SensorTypeIdDTO} from "@frontend-services/data-decoder/dto";
import {SensorTypeId} from "@frontend-services/data-decoder/model";

export class SensorTypeMapper {
  static dtoToModel(dto: SensorTypeIdDTO): SensorTypeId {
    return new SensorTypeId(dto.type);
  }

  static modelToDto(model: SensorTypeId): SensorTypeIdDTO {
    return {type: model.type};
  }
}
