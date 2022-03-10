import { DeviceDTO } from '@frontend-services/device-records/dto';
import { Device } from '@frontend-services/device-records/model';

export class DeviceMapper {
  static dtoToModel(dto: DeviceDTO): Device {
    return new Device(dto.id, dto.name);
  }

  static modelToDto(model: Device): DeviceDTO {
    return { id: model.id, name: model.name };
  }
}
