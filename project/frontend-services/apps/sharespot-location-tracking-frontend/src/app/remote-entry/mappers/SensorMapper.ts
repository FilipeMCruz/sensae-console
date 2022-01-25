import {HistorySensorDTO, SensorDataDTO} from '../dtos/SensorDTO';
import {DeviceCoordinates} from '../model/DeviceCoordinates';
import {DeviceData} from "../model/DeviceData";
import {RecordEntry} from "../model/RecordEntry";
import {Device} from "../model/Device";
import {DeviceHistory} from "../model/DeviceHistory";
import {DeviceStatus, MotionType} from "../model/DeviceStatus";
import {DeviceDataDetails} from "../model/DeviceDataDetails";

export class SensorMapper {

  static dtoToModel(value: SensorDataDTO): DeviceData {
    const coordinates = new DeviceCoordinates(value.data.gps.latitude, value.data.gps.longitude);
    let status;
    if (value.data.status.motion == "ACTIVE") {
      status = new DeviceStatus(MotionType.ACTIVE);
    } else if (value.data.status.motion == "INACTIVE") {
      status = new DeviceStatus(MotionType.INACTIVE);
    } else {
      status = new DeviceStatus(MotionType.UNKWOWN);
    }
    const details = new DeviceDataDetails(coordinates, status);
    const entries = value.device.records.map(e => new RecordEntry(e.label, e.content));
    const sensor = new Device(value.device.id, value.device.name, entries);
    return new DeviceData(value.dataId, sensor, new Date(Number(value.reportedAt)), details);
  }

  static dtoToModelHistory(dto: HistorySensorDTO): Array<DeviceHistory> {
    return dto.history.map(h => {
      const gpsData = h.data
        //TODO: remove once we have a way to deal with errors
        // .filter(d => d.longitude < 2 && d.longitude > -2 && d.latitude < 2 && d.latitude > -2)
        .map(d => new DeviceCoordinates(d.latitude, d.longitude));
      return new DeviceHistory(h.deviceName,
        h.deviceId,
        Number(h.startTime),
        Number(h.endTime),
        +h.distance.toFixed(2),
        gpsData);
    });
  }
}
