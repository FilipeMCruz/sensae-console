import {
  DataTransformation,
  PropertyName,
  PropertyTransformation,
} from '@frontend-services/data-processor-model';
import { SensorTypeMapper } from './SensorTypeMapper';
import {
  DataTransformationDTO,
  PropertyNameDTO,
  PropertyTransformationDTO,
} from '@frontend-services/data-processor-dto';

export class DataTransformationMapper {
  static dtoToModel(dto: DataTransformationDTO): DataTransformation {
    const sensorTypeId = SensorTypeMapper.dtoToModel(dto.data);
    const entries = dto.entries.map(
      (e) =>
        new PropertyTransformation(
          e.oldPath,
          DataTransformationMapper.propertyNameDtoToModel(e.newPath)
        )
    );
    return new DataTransformation(sensorTypeId, entries);
  }

  private static propertyNameDtoToModel(dto: PropertyNameDTO): PropertyName {
    switch (dto) {
      case PropertyNameDTO.DATA_ID:
        return PropertyName.DATA_ID;
      case PropertyNameDTO.DEVICE_ID:
        return PropertyName.DEVICE_ID;
      case PropertyNameDTO.DEVICE_NAME:
        return PropertyName.DEVICE_NAME;
      case PropertyNameDTO.REPORTED_AT:
        return PropertyName.REPORTED_AT;
      case PropertyNameDTO.LATITUDE:
        return PropertyName.LATITUDE;
      case PropertyNameDTO.LONGITUDE:
        return PropertyName.LONGITUDE;
      case PropertyNameDTO.MOTION:
        return PropertyName.MOTION;
      case PropertyNameDTO.VELOCITY:
        return PropertyName.VELOCITY;
      case PropertyNameDTO.HUMIDITY:
        return PropertyName.HUMIDITY;
      case PropertyNameDTO.PRESSURE:
        return PropertyName.PRESSURE;
      case PropertyNameDTO.TEMPERATURE:
        return PropertyName.TEMPERATURE;
      case PropertyNameDTO.AQI:
        return PropertyName.AQI;
      case PropertyNameDTO.INVALID:
        return PropertyName.INVALID;
    }
  }

  static modelToDto(model: DataTransformation): DataTransformationDTO {
    const entries: PropertyTransformationDTO[] = model.entries.map((e) => ({
      oldPath: e.oldPath,
      newPath: DataTransformationMapper.propertyNameModelToDto(e.newPath),
    }));
    return { data: { type: model.data.type }, entries: entries };
  }

  private static propertyNameModelToDto(model: PropertyName): PropertyNameDTO {
    switch (model) {
      case PropertyName.DATA_ID:
        return PropertyNameDTO.DATA_ID;
      case PropertyName.DEVICE_ID:
        return PropertyNameDTO.DEVICE_ID;
      case PropertyName.DEVICE_NAME:
        return PropertyNameDTO.DEVICE_NAME;
      case PropertyName.REPORTED_AT:
        return PropertyNameDTO.REPORTED_AT;
      case PropertyName.LATITUDE:
        return PropertyNameDTO.LATITUDE;
      case PropertyName.LONGITUDE:
        return PropertyNameDTO.LONGITUDE;
      case PropertyName.MOTION:
        return PropertyNameDTO.MOTION;
      case PropertyName.VELOCITY:
        return PropertyNameDTO.VELOCITY;
      case PropertyName.HUMIDITY:
        return PropertyNameDTO.HUMIDITY;
      case PropertyName.PRESSURE:
        return PropertyNameDTO.PRESSURE;
      case PropertyName.TEMPERATURE:
        return PropertyNameDTO.TEMPERATURE;
      case PropertyName.AQI:
        return PropertyNameDTO.AQI;
      case PropertyName.INVALID:
        return PropertyNameDTO.INVALID;
    }
  }
}
