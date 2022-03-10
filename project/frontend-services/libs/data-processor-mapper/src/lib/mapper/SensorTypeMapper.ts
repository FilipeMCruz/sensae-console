import { SensorTypeId } from '@frontend-services/data-processor-model';
import { SensorTypeIdDTO } from '@frontend-services/data-processor-dto';

export class SensorTypeMapper {
  static dtoToModel(dto: SensorTypeIdDTO): SensorTypeId {
    return new SensorTypeId(dto.type);
  }

  static modelToDto(model: SensorTypeId): SensorTypeIdDTO {
    return { type: model.type };
  }
}
