import {DataTransformationMapper} from './DataTransformationMapper';
import {DataTransformation} from '@frontend-services/data-processor/model';
import {DataTransformationInput, DataTransformationResult} from '@frontend-services/data-processor/dto';

export class DataTransformationRegisterMapper {
  static modelToDto(model: DataTransformation): DataTransformationInput {
    return {transformation: DataTransformationMapper.modelToDto(model)};
  }

  static dtoToModel(dto: DataTransformationResult): DataTransformation {
    return DataTransformationMapper.dtoToModel(dto.index);
  }
}
