import {
  DeviceTypeDTO,
  GPSDataDetailsDTO,
  HumidityDataDetailsDTO,
  IlluminanceDataDetailsDTO,
  ParkSensorDataDetailsDTO, ParkSensorDataHistoryDetailsDTO, SensorDataDetailsDTO, SensorDataHistoryDetailsDTO,
  SoilMoistureDataDetailsDTO,
  StoveSensorDataDetailsDTO, StoveSensorDataHistoryDetailsDTO, TemperatureDataDetailsDTO,
  ValveDataDetailsDTO, ValveDataHistoryDetailsDTO,
  ValveStatusDataDetailsDTO,
  ValveStatusDataDetailsTypeDTO
} from "@frontend-services/smart-irrigation/dto";
import {
  GPSDataDetails,
  HumidityDataDetails,
  IlluminanceDataDetails,
  ParkSensorDataDetails, ParkSensorDataHistoryDetails, SensorDataDetails, SensorDataHistoryDetails,
  SoilMoistureDataDetails,
  StoveSensorDataDetails, StoveSensorDataHistoryDetails,
  TemperatureDataDetails,
  ValveDataDetails, ValveDataHistoryDetails,
  ValveStatusDataDetails,
  ValveStatusDataDetailsType
} from "@frontend-services/smart-irrigation/model";

export class DataDetailsMapper {
  static gpsDtoToModel(dto: GPSDataDetailsDTO): GPSDataDetails {
    return new GPSDataDetails(dto.latitude, dto.longitude, dto.altitude);
  }

  static soilMoistureDtoToModel(dto: SoilMoistureDataDetailsDTO): SoilMoistureDataDetails {
    return new SoilMoistureDataDetails(dto.percentage);
  }

  static humidityDtoToModel(dto: HumidityDataDetailsDTO): HumidityDataDetails {
    return new HumidityDataDetails(dto.gramsPerCubicMeter);
  }

  static illuminanceDtoToModel(dto: IlluminanceDataDetailsDTO): IlluminanceDataDetails {
    return new IlluminanceDataDetails(dto.lux);
  }

  static temperatureDtoToModel(dto: TemperatureDataDetailsDTO): TemperatureDataDetails {
    return new TemperatureDataDetails(dto.celsius);
  }

  static valveDtoToModel(dto: ValveStatusDataDetailsDTO): ValveStatusDataDetails {
    switch (dto.status) {
      case ValveStatusDataDetailsTypeDTO.CLOSE:
        return new ValveStatusDataDetails(ValveStatusDataDetailsType.CLOSED);
      case ValveStatusDataDetailsTypeDTO.OPEN:
        return new ValveStatusDataDetails(ValveStatusDataDetailsType.OPEN);
      default:
        return new ValveStatusDataDetails(ValveStatusDataDetailsType.CLOSED);
    }
  }

  static dataDtoToModel(dto: SensorDataDetailsDTO, type: DeviceTypeDTO): SensorDataDetails {
    switch (type) {
      case DeviceTypeDTO.PARK_SENSOR:
        return DataDetailsMapper.parkDataDtoToModel(<ParkSensorDataDetailsDTO>dto);
      case DeviceTypeDTO.STOVE_SENSOR:
        return DataDetailsMapper.stoveDataDtoToModel(<StoveSensorDataDetailsDTO>dto);
      case DeviceTypeDTO.VALVE:
        return DataDetailsMapper.valveDataDtoToModel(<ValveDataDetailsDTO>dto);
    }
  }

  static valveDataDtoToModel(dto: ValveDataDetailsDTO): ValveDataDetails {
    return new ValveDataDetails(DataDetailsMapper.gpsDtoToModel(dto.gps),
      DataDetailsMapper.valveDtoToModel(dto.valve));
  }

  static parkDataDtoToModel(dto: ParkSensorDataDetailsDTO): ParkSensorDataDetails {
    return new ParkSensorDataDetails(DataDetailsMapper.gpsDtoToModel(dto.gps),
      DataDetailsMapper.soilMoistureDtoToModel(dto.soilMoisture),
      DataDetailsMapper.illuminanceDtoToModel(dto.illuminance));
  }

  static stoveDataDtoToModel(dto: StoveSensorDataDetailsDTO): StoveSensorDataDetails {
    return new StoveSensorDataDetails(DataDetailsMapper.gpsDtoToModel(dto.gps),
      DataDetailsMapper.temperatureDtoToModel(dto.temperature),
      DataDetailsMapper.humidityDtoToModel(dto.humidity));
  }

  static historyDtoToModel(dto: SensorDataHistoryDetailsDTO, type: DeviceTypeDTO): SensorDataHistoryDetails {
    switch (type) {
      case DeviceTypeDTO.PARK_SENSOR:
        return DataDetailsMapper.parkHistoryDataDtoToModel(<ParkSensorDataHistoryDetailsDTO>dto);
      case DeviceTypeDTO.STOVE_SENSOR:
        return DataDetailsMapper.stoveHistoryDataDtoToModel(<StoveSensorDataHistoryDetailsDTO>dto);
      case DeviceTypeDTO.VALVE:
        return DataDetailsMapper.valveHistoryDataDtoToModel(<ValveDataHistoryDetailsDTO>dto);
    }
  }

  static parkHistoryDataDtoToModel(dto: ParkSensorDataHistoryDetailsDTO): ParkSensorDataHistoryDetails {
    return new ParkSensorDataHistoryDetails(dto.id, new Date(Number(dto.reportedAt) * 1000),
      DataDetailsMapper.soilMoistureDtoToModel(dto.soilMoisture),
      DataDetailsMapper.illuminanceDtoToModel(dto.illuminance));
  }

  static stoveHistoryDataDtoToModel(dto: StoveSensorDataHistoryDetailsDTO): StoveSensorDataHistoryDetails {
    return new StoveSensorDataHistoryDetails(dto.id, new Date(Number(dto.reportedAt) * 1000),
      DataDetailsMapper.temperatureDtoToModel(dto.temperature),
      DataDetailsMapper.humidityDtoToModel(dto.humidity));
  }

  static valveHistoryDataDtoToModel(dto: ValveDataHistoryDetailsDTO): ValveDataHistoryDetails {
    return new ValveDataHistoryDetails(dto.id, new Date(Number(dto.reportedAt) * 1000),
      DataDetailsMapper.valveDtoToModel(dto.valve));
  }
}
