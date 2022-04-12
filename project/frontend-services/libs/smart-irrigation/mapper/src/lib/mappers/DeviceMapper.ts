import {DeviceDTO, DeviceTypeDTO} from "@frontend-services/smart-irrigation/dto";
import {Device, DeviceType, RecordEntry} from "@frontend-services/smart-irrigation/model";

export class DeviceMapper {
  static dtoToModel(dto: DeviceDTO): Device {
    const records = dto.records.map(d => new RecordEntry(d.label, d.content));
    switch (dto.type) {
      case DeviceTypeDTO.PARK_SENSOR:
        return new Device(dto.id, DeviceType.PARK_SENSOR, dto.name, records);
      case DeviceTypeDTO.STOVE_SENSOR:
        return new Device(dto.id, DeviceType.STOVE_SENSOR, dto.name, records);
      case DeviceTypeDTO.VALVE:
        return new Device(dto.id, DeviceType.VALVE, dto.name, records);
    }
  }
}
