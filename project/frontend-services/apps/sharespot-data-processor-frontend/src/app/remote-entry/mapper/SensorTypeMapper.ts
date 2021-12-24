import {SensorTypeIdDTO} from "../dtos/DataTransformationDTO";
import {SensorTypeId} from "../model/SensorTypeId";

export class SensorTypeMapper {

  static dtoToModel(dto: SensorTypeIdDTO): SensorTypeId {
    return new SensorTypeId(dto.type);
  }

  static modelToDto(model: SensorTypeId): SensorTypeIdDTO {
    return {type: model.type}
  }
}
