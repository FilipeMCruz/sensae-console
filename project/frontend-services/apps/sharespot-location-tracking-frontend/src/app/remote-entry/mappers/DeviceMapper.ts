import {DeviceDTO} from "../dtos/SensorDTO";
import {RecordEntry} from "../model/livedata/RecordEntry";
import {Device} from "../model/Device";

export class DeviceMapper {

  static dtoToModel(value: DeviceDTO): Device {
    const entries = value.records.map(e => new RecordEntry(e.label, e.content));
    return new Device(value.id, value.name, entries);
  }
}
