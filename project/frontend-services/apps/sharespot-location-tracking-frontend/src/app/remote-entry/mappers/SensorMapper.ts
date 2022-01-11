import {FilteredByContentSensorDTO, FilteredSensorDTO, GPSSensorDataHistory, SensorDTO} from '../dtos/SensorDTO';
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
    return new GPSSensorData(value.dataId, sensor, new Date(Number(value.reportedAt) * 1000), coordinates);
  }

  static dtoToModelHistory(dto: GPSSensorDataHistory): DeviceHistory {
    const gpsData = dto.data.map(d => new SensorCoordinates(d.latitude, d.longitude));
    return new DeviceHistory(dto.deviceName, dto.deviceId, Number(dto.startTime), Number(dto.endTime), gpsData)
  }
}
