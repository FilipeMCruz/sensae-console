import {DataTransformation, PropertyName, PropertyTransformation,} from '@frontend-services/data-processor/model';
import {SensorTypeMapper} from './SensorTypeMapper';
import {
  DataTransformationDTO,
  PropertyNameDTO,
  PropertyTransformationDTO,
} from '@frontend-services/data-processor/dto';

export class DataTransformationMapper {
  static dtoToModel(dto: DataTransformationDTO): DataTransformation {
    const sensorTypeId = SensorTypeMapper.dtoToModel(dto.data);
    const entries = dto.entries.map(
      (e) =>
        new PropertyTransformation(
          e.oldPath,
          DataTransformationMapper.propertyNameDtoToModel(e.newPath),
          e.sensorID
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
      case PropertyNameDTO.TEMPERATURE:
        return PropertyName.TEMPERATURE;
      case PropertyNameDTO.AQI:
        return PropertyName.AQI;
      case PropertyNameDTO.SOIL_MOISTURE:
        return PropertyName.SOIL_MOISTURE;
      case PropertyNameDTO.ILLUMINANCE:
        return PropertyName.ILLUMINANCE;
      case PropertyNameDTO.ALTITUDE:
        return PropertyName.ALTITUDE;
      case PropertyNameDTO.BATTERY_PERCENTAGE:
        return PropertyName.BATTERY_PERCENTAGE;
      case PropertyNameDTO.BATTERY_VOLTS:
        return PropertyName.BATTERY_VOLTS;
      case PropertyNameDTO.TRIGGER:
        return PropertyName.TRIGGER;
      case PropertyNameDTO.INVALID:
        return PropertyName.INVALID;
      case PropertyNameDTO.AIR_HUMIDITY_GRAMS_PER_CUBIC_METER:
        return PropertyName.AIR_HUMIDITY_GRAMS_PER_CUBIC_METER;
      case PropertyNameDTO.AIR_HUMIDITY_RELATIVE_PERCENTAGE:
        return PropertyName.AIR_HUMIDITY_RELATIVE_PERCENTAGE;
      case PropertyNameDTO.AIR_PRESSURE:
        return PropertyName.AIR_PRESSURE;
      case PropertyNameDTO.BATTERY_MAX_VOLTS:
        return PropertyName.BATTERY_MAX_VOLTS;
      case PropertyNameDTO.BATTERY_MIN_VOLTS:
        return PropertyName.BATTERY_MIN_VOLTS;
      case PropertyNameDTO.CO2:
        return PropertyName.CO2;
      case PropertyNameDTO.CO:
        return PropertyName.CO
      case PropertyNameDTO.NH3:
        return PropertyName.NH3;
      case PropertyNameDTO.PH:
        return PropertyName.PH;
      case PropertyNameDTO.DISTANCE:
        return PropertyName.DISTANCE;
      case PropertyNameDTO.MAX_DISTANCE:
        return PropertyName.MAX_DISTANCE;
      case PropertyNameDTO.MIN_DISTANCE:
        return PropertyName.MIN_DISTANCE;
      case PropertyNameDTO.NO2:
        return PropertyName.NO2;
      case PropertyNameDTO.O3:
        return PropertyName.O3;
      case PropertyNameDTO.VOC:
        return PropertyName.VOC;
      case PropertyNameDTO.WATER_PRESSURE:
        return PropertyName.WATER_PRESSURE;
      case PropertyNameDTO.SOIL_CONDUCTIVITY:
        return PropertyName.SOIL_CONDUCTIVITY;
      case PropertyNameDTO.OCCUPATION:
        return PropertyName.OCCUPATION;
      case PropertyNameDTO.PM2_5:
        return PropertyName.PM2_5;
      case PropertyNameDTO.PM10:
        return PropertyName.PM10;
    }
  }

  static modelToDto(model: DataTransformation): DataTransformationDTO {
    const entries: PropertyTransformationDTO[] = model.entries.map((e) => ({
      oldPath: e.oldPath,
      newPath: DataTransformationMapper.propertyNameModelToDto(e.newPath),
      sensorID: e.subSensorId,
    }));
    return {data: {type: model.data.type}, entries: entries};
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
      case PropertyName.TEMPERATURE:
        return PropertyNameDTO.TEMPERATURE;
      case PropertyName.AQI:
        return PropertyNameDTO.AQI;
      case PropertyName.SOIL_MOISTURE:
        return PropertyNameDTO.SOIL_MOISTURE;
      case PropertyName.ILLUMINANCE:
        return PropertyNameDTO.ILLUMINANCE;
      case PropertyName.ALTITUDE:
        return PropertyNameDTO.ALTITUDE;
      case PropertyName.BATTERY_PERCENTAGE:
        return PropertyNameDTO.BATTERY_PERCENTAGE;
      case PropertyName.BATTERY_VOLTS:
        return PropertyNameDTO.BATTERY_VOLTS;
      case PropertyName.TRIGGER:
        return PropertyNameDTO.TRIGGER;
      case PropertyName.INVALID:
        return PropertyNameDTO.INVALID;
      case PropertyName.AIR_HUMIDITY_GRAMS_PER_CUBIC_METER:
        return PropertyNameDTO.AIR_HUMIDITY_GRAMS_PER_CUBIC_METER;
      case PropertyName.AIR_HUMIDITY_RELATIVE_PERCENTAGE:
        return PropertyNameDTO.AIR_HUMIDITY_RELATIVE_PERCENTAGE;
      case PropertyName.AIR_PRESSURE:
        return PropertyNameDTO.AIR_PRESSURE;
      case PropertyName.BATTERY_MAX_VOLTS:
        return PropertyNameDTO.BATTERY_MAX_VOLTS;
      case PropertyName.BATTERY_MIN_VOLTS:
        return PropertyNameDTO.BATTERY_MIN_VOLTS;
      case PropertyName.CO2:
        return PropertyNameDTO.CO2;
      case PropertyName.CO:
        return PropertyNameDTO.CO
      case PropertyName.NH3:
        return PropertyNameDTO.NH3;
      case PropertyName.PH:
        return PropertyNameDTO.PH;
      case PropertyName.DISTANCE:
        return PropertyNameDTO.DISTANCE;
      case PropertyName.MAX_DISTANCE:
        return PropertyNameDTO.MAX_DISTANCE;
      case PropertyName.MIN_DISTANCE:
        return PropertyNameDTO.MIN_DISTANCE;
      case PropertyName.NO2:
        return PropertyNameDTO.NO2;
      case PropertyName.O3:
        return PropertyNameDTO.O3;
      case PropertyName.VOC:
        return PropertyNameDTO.VOC;
      case PropertyName.WATER_PRESSURE:
        return PropertyNameDTO.WATER_PRESSURE;
      case PropertyName.SOIL_CONDUCTIVITY:
        return PropertyNameDTO.SOIL_CONDUCTIVITY;
      case PropertyName.OCCUPATION:
        return PropertyNameDTO.OCCUPATION;
      case PropertyName.PM2_5:
        return PropertyNameDTO.PM2_5;
      case PropertyName.PM10:
        return PropertyNameDTO.PM10;
    }
  }
}
