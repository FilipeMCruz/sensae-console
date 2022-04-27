import {DeviceDTO} from '@frontend-services/device-management/dto';
import {Device} from '@frontend-services/device-management/model';

export class DeviceMapper {
  static dtoToModel(dto: DeviceDTO): Device {
    return new Device(dto.id, dto.name, dto.downlink);
  }

  static modelToDto(model: Device): DeviceDTO {
    return {id: model.id, name: model.name, downlink: model.downlink};
  }
}
