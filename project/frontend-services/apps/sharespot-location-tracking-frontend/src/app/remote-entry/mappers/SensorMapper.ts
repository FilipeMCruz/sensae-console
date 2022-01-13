import {FilteredByContentSensorDTO, FilteredSensorDTO, HistorySensorDTO, SensorDTO} from '../dtos/SensorDTO';
import {SensorCoordinates} from '../model/SensorCoordinates';
import {GPSSensorData} from "../model/GPSSensorData";
import {RecordEntry} from "../model/RecordEntry";
import {Device} from "../model/Device";
import {DeviceHistory} from "../model/DeviceHistory";

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
    const entries = value.device.records.map(e => new RecordEntry(e.label, e.content));
    const sensor = new Device(value.device.id, value.device.name, entries);
    console.log(value.reportedAt);
    return new GPSSensorData(value.dataId, sensor, new Date(Number(value.reportedAt)), coordinates);
  }

  static dtoToModelHistory(dto: HistorySensorDTO): DeviceHistory {
    const gpsData = dto.history.data.map(d => new SensorCoordinates(d.latitude, d.longitude));
    return new DeviceHistory(dto.history.deviceName, dto.history.deviceId, Number(dto.history.startTime), Number(dto.history.endTime), gpsData)
  }
}
