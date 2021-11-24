import {Device} from "../model/Device";
import {DeviceDTO} from "../dtos/RecordsDTO";

export class DeviceMapper {

  static dtoToModel(dto: DeviceDTO): Device {
    return new Device(dto.deviceId);
  }

  static modelToDto(model: Device): DeviceDTO {
    return {deviceId: model.deviceId}
  }
}
