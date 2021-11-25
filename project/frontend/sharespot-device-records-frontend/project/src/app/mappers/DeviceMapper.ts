import {Device} from "../model/Device";
import {DeviceIdDTO} from "../dtos/RecordsDTO";

export class DeviceMapper {

  static dtoToModel(dto: DeviceIdDTO): Device {
    return new Device(dto.deviceId);
  }

  static modelToDto(model: Device): DeviceIdDTO {
    return {deviceId: model.deviceId}
  }
}
