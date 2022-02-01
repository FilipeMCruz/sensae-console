import {DataTransformationDTO, PropertyNameDTO, PropertyTransformationDTO} from "../dtos/DataTransformationDTO";
import {DataTransformation} from "../model/DataTransformation";
import {SensorTypeMapper} from "./SensorTypeMapper";
import {PropertyTransformation} from "../model/PropertyTransformation";
import {PropertyName} from "../model/PropertyName";

export class DataTransformationMapper {

  static dtoToModel(dto: DataTransformationDTO): DataTransformation {
    const sensorTypeId = SensorTypeMapper.dtoToModel(dto.data);
    const entries = dto.entries.map(e => new PropertyTransformation(e.oldPath, DataTransformationMapper.propertyNameDtoToModel(e.newPath)));
    return new DataTransformation(sensorTypeId, entries);
  }

  private static propertyNameDtoToModel(dto: PropertyNameDTO): PropertyName {
    if (dto === PropertyNameDTO.DEVICE_NAME) {
      return PropertyName.DEVICE_NAME;
    } else if (dto === PropertyNameDTO.DATA_ID) {
      return PropertyName.DATA_ID;
    } else if (dto === PropertyNameDTO.DEVICE_ID) {
      return PropertyName.DEVICE_ID;
    } else if (dto === PropertyNameDTO.DEVICE_RECORDS) {
      return PropertyName.DEVICE_RECORDS;
    } else if (dto === PropertyNameDTO.LATITUDE) {
      return PropertyName.LATITUDE;
    } else if (dto === PropertyNameDTO.LONGITUDE) {
      return PropertyName.LONGITUDE;
    } else if (dto === PropertyNameDTO.MOTION) {
      return PropertyName.MOTION;
    } else if (dto === PropertyNameDTO.TEMPERATURE) {
      return PropertyName.TEMPERATURE;
    } else if (dto === PropertyNameDTO.REPORTED_AT) {
      return PropertyName.REPORTED_AT;
    }
    return PropertyName.INVALID;
  }

  static modelToDto(model: DataTransformation): DataTransformationDTO {
    const entries: PropertyTransformationDTO[] = model.entries.map(e => ({
      oldPath: e.oldPath,
      newPath: DataTransformationMapper.propertyNameModelToDto(e.newPath)
    }));
    return {data: {type: model.data.type}, entries: entries}
  }

  private static propertyNameModelToDto(model: PropertyName): PropertyNameDTO {
    if (model === PropertyName.DEVICE_NAME) {
      return PropertyNameDTO.DEVICE_NAME;
    } else if (model === PropertyName.DATA_ID) {
      return PropertyNameDTO.DATA_ID;
    } else if (model === PropertyName.DEVICE_ID) {
      return PropertyNameDTO.DEVICE_ID;
    } else if (model === PropertyName.DEVICE_RECORDS) {
      return PropertyNameDTO.DEVICE_RECORDS;
    } else if (model === PropertyName.LATITUDE) {
      return PropertyNameDTO.LATITUDE;
    } else if (model === PropertyName.LONGITUDE) {
      return PropertyNameDTO.LONGITUDE;
    } else if (model === PropertyName.MOTION) {
      return PropertyNameDTO.MOTION;
    } else if (model === PropertyName.TEMPERATURE) {
      return PropertyNameDTO.TEMPERATURE;
    } else if (model === PropertyName.REPORTED_AT) {
      return PropertyNameDTO.REPORTED_AT;
    }
    return PropertyNameDTO.INVALID;
  }
}
