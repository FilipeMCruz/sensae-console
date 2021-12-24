import {DataTransformation} from "../model/DataTransformation";
import {DataTransformationInput} from "../dtos/DataTransformationDTO";
import {DataTransformationMapper} from "./DataTransformationMapper";

export class DataTransformationRegisterMapper {
  static modelToDto(model: DataTransformation): DataTransformationInput {
    return {index: DataTransformationMapper.modelToDto(model)};
  }

  static dtoToModel(dto: DataTransformationInput): DataTransformation {
    return DataTransformationMapper.dtoToModel(dto.index);
  }
}
