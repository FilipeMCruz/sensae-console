import {CommandDTO} from '@frontend-services/device-management/dto';
import {Device, DeviceCommand} from '@frontend-services/device-management/model';

export class CommandMapper {
  static dtoToModel(dto: CommandDTO): Device {
    return new Device(dto.deviceId, "", "");
  }

  static modelToDto(event: DeviceCommand, device: Device): CommandDTO {
    return {commandId: event.id, deviceId: device.id};
  }
}
