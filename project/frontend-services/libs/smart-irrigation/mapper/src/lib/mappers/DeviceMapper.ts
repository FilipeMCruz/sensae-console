import {DeviceDTO, DeviceTypeDTO} from "@frontend-services/smart-irrigation/dto";
import {Device, DeviceId, DeviceName, DeviceType, RecordEntry} from "@frontend-services/smart-irrigation/model";

export class DeviceMapper {
  static dtoToModel(dto: DeviceDTO): Device {
    const id = new DeviceId(dto.id);
    const name = new DeviceName(dto.name);
    const records = dto.records.map(d => new RecordEntry(d.label, d.content));
    switch (dto.type) {
      case DeviceTypeDTO.PARK_SENSOR:
        return new Device(id, DeviceType.PARK_SENSOR, name, records, dto.remoteControl, false);
      case DeviceTypeDTO.STOVE_SENSOR:
        return new Device(id, DeviceType.STOVE_SENSOR, name, records, dto.remoteControl, false);
      case DeviceTypeDTO.VALVE:
        return new Device(id, DeviceType.VALVE, name, records, dto.remoteControl, false);
    }
  }
}
