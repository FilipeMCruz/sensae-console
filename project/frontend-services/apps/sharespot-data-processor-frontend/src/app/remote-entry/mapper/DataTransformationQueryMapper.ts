import {DataTransformationQuery} from "../dtos/DataTransformationDTO";
import {DataTransformation} from "../model/DataTransformation";
import {DataTransformationMapper} from "./DataTransformationMapper";

export class DataTransformationQueryMapper {
  static dtoToModel(dto: DataTransformationQuery): Array<DataTransformation> {
    return dto.transformation.map(e => DataTransformationMapper.dtoToModel(e));
  }
}
