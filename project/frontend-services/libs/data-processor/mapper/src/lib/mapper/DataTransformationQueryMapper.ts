import {DataTransformationMapper} from './DataTransformationMapper';
import {DataTransformation} from '@frontend-services/data-processor/model';
import {DataTransformationQuery} from '@frontend-services/data-processor/dto';

export class DataTransformationQueryMapper {
  static dtoToModel(dto: DataTransformationQuery): Array<DataTransformation> {
    return dto.transformation.map((e) =>
      DataTransformationMapper.dtoToModel(e)
    );
  }
}
