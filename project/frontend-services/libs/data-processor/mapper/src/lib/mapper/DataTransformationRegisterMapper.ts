import {DataTransformationMapper} from './DataTransformationMapper';
import {DataTransformation} from '@frontend-services/data-processor/model';
import {DataTransformationInput} from '@frontend-services/data-processor/dto';

export class DataTransformationRegisterMapper {
  static modelToDto(model: DataTransformation): DataTransformationInput {
    return {index: DataTransformationMapper.modelToDto(model)};
  }

  static dtoToModel(dto: DataTransformationInput): DataTransformation {
    return DataTransformationMapper.dtoToModel(dto.index);
  }
}
