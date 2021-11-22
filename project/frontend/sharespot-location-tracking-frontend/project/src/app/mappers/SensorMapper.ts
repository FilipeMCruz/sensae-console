import {FilteredSensorDTO, SensorDTO} from '../dtos/SensorDTO';
import {SensorCoordinates} from '../model/SensorCoordinates';
import {GPSSensorData} from "../model/GPSSensorData";
import {RecordEntry} from "../model/RecordEntry";

export class SensorMapper {

  static dtoToModel(dto: SensorDTO | FilteredSensorDTO): GPSSensorData {
    let value;
    if ("locations" in dto) {
      value = dto.locations;
    } else {
      value = dto.location;
    }
    let coordinates = new SensorCoordinates(value.data.gps.latitude, value.data.gps.longitude);
    let entries = value.record.map(e => new RecordEntry(e.label, e.content));
    return new GPSSensorData(value.dataId, value.deviceId, new Date(Number(value.reportedAt)), coordinates, entries);
  }
}
