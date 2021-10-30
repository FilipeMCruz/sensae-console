import {SensorDTO} from '../dtos/SensorDTO';
import {SensorCoordinates} from '../model/SensorCoordinates';
import {GPSSensorData} from "../model/GPSSensorData";

export class SensorMapper {
  static dtoToModel(dto: SensorDTO): GPSSensorData {
    let value = dto.locations;
    let coordinates = new SensorCoordinates(value.data.latitude, value.data.longitude);
    return new GPSSensorData(value.dataId, value.deviceId, new Date(Number(value.reportDate)), coordinates);
  }
}
