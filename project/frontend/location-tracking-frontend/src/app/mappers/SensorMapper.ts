import {FilteredSensorDTO, SensorDTO} from '../dtos/SensorDTO';
import {SensorCoordinates} from '../model/SensorCoordinates';
import {GPSSensorData} from "../model/GPSSensorData";

export class SensorMapper {

  static dtoToModel(dto: SensorDTO | FilteredSensorDTO): GPSSensorData {
    let value;
    if ("locations" in dto) {
      value = dto.locations;
    } else {
      value = dto.location;
    }
    let coordinates = new SensorCoordinates(value.data.latitude, value.data.longitude);
    return new GPSSensorData(value.dataId, value.deviceId, new Date(Number(value.reportedAt)), coordinates);
  }
}
