import {FilteredByContentSensorDTO, FilteredSensorDTO, SensorDTO} from '../dtos/SensorDTO';
import {SensorCoordinates} from '../model/SensorCoordinates';
import {GPSSensorData} from "../model/GPSSensorData";
import {RecordEntry} from "../model/RecordEntry";
import {Sensor} from "../model/Sensor";

export class SensorMapper {

  static dtoToModel(dto: SensorDTO | FilteredSensorDTO | FilteredByContentSensorDTO): GPSSensorData {
    let value;
    if ("locations" in dto) {
      value = dto.locations;
    } else if ("locationByContent" in dto) {
      value = dto.locationByContent;
    } else {
      value = dto.location;
    }
    const coordinates = new SensorCoordinates(value.data.gps.latitude, value.data.gps.longitude);
    const entries = value.record.map(e => new RecordEntry(e.label, e.content));
    const sensor = new Sensor(value.device.id, value.device.name);
    return new GPSSensorData(value.dataId, sensor, new Date(Number(value.reportedAt)), coordinates, entries);
  }
}
